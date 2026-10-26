<?php
// Este es el api de inscripciones.php

// 1. Header JSON y conexion
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

// 2. Leer el cuerpo JSON enviado por Retrofit desde Android
$jsonInput = file_get_contents("php://input");
$data = json_decode($jsonInput, true);

// 3. Sacar los datos
$id_curso = isset($data['id_curso']) ? (int)$data['id_curso'] : 0;
$matricula = isset($data['matricula']) ? trim($data['matricula']) : '';
$correo = isset($data['correo']) ? trim($data['correo']) : '';
$fecha_registro = isset($data['fecha_registro']) ? trim($data['fecha_registro']) : '';

// 4. Valida si falta algo
if ($id_curso <= 0 || empty($matricula) || empty($correo) || empty($fecha_registro)) {
    header("HTTP/1.1 400 Bad Request");
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe ingresar curso, matricula, correo y fecha"
    ]);
    exit;
}

try {
    // 5. Validar la fecha (en formato yyyy-mm-dd)
    $fecha = DateTime::createFromFormat('Y-m-d', $fecha_registro);
    if (!$fecha || $fecha->format('Y-m-d') !== $fecha_registro) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "La fecha no es valida (use AAAA-MM-DD)"
        ]);
        exit;
    }

    // 6. Busca la matricula y correo del alumno en la tabla de alumnos
    $stmt = $pdo->prepare("SELECT nombre_alumno FROM alumnos WHERE matricula = ? AND correo = ?");
    $stmt->execute([$matricula, $correo]);
    $alumno = $stmt->fetch();

    if (!$alumno) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "Matricula o correo incorrectos"
        ]);
        exit;
    }

    // 7. Busca y guarda el cupo
    $stmt = $pdo->prepare("SELECT nombre_curso, cupo FROM cursos WHERE id_curso = ?");
    $stmt->execute([$id_curso]);
    $curso = $stmt->fetch();

    if (!$curso) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "El curso no existe"
        ]);
        exit;
    }

    // 8. Valida si ya esta inscrito
    $stmt = $pdo->prepare("SELECT COUNT(*) FROM inscripciones WHERE id_curso = ? AND matricula = ?");
    $stmt->execute([$id_curso, $matricula]);
    if ((int)$stmt->fetchColumn() > 0) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "Ya estas inscrito en este curso"
        ]);
        exit;
    }

    // 9. Valida si hay cupo
    $stmt = $pdo->prepare("SELECT COUNT(*) FROM inscripciones WHERE id_curso = ?");
    $stmt->execute([$id_curso]);
    $inscritos = (int)$stmt->fetchColumn();

    if ($inscritos >= (int)$curso['cupo']) {
        echo json_encode([
            "exito" => false,
            "mensaje" => "El curso ya no tiene cupo"
        ]);
        exit;
    }

    // 10. Insertar la inscripcion
    $stmt = $pdo->prepare("INSERT INTO inscripciones (id_curso, matricula, fecha_registro) VALUES (?, ?, ?)");
    $stmt->execute([$id_curso, $matricula, $fecha_registro]);

    echo json_encode([
        "exito" => true,
        "mensaje" => "¡Inscripción confirmada a " . $curso['nombre_curso'] . "!",
        "id_inscripcion" => (int)$pdo->lastInsertId(),
        "nombre_curso" => $curso['nombre_curso'],
        "matricula" => $matricula,
        "fecha_registro" => $fecha_registro
    ]);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode([
        "exito" => false,
    "mensaje" => "Error en el servidor: " . $e->getMessage()
    ]);
}
?>
