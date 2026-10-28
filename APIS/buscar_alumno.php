<?php
// Busca el correo del alumno por su matricula
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

$matricula = isset($_GET['matricula']) ? trim($_GET['matricula']) : '';

if (empty($matricula)) {
    echo json_encode(["exito" => false, "mensaje" => "Debe ingresar la matricula"]);
    exit;
}

try {
    $stmt = $pdo->prepare("SELECT nombre_alumno, correo FROM alumnos WHERE matricula = ?");
    $stmt->execute([$matricula]);
    $alumno = $stmt->fetch();

    if (!$alumno) {
        echo json_encode(["exito" => false, "mensaje" => "La matricula no existe"]);
        exit;
    }

    echo json_encode([
        "exito" => true,
        "mensaje" => "Alumno encontrado",
        "nombre_alumno" => $alumno['nombre_alumno'],
        "correo" => $alumno['correo']
    ]);
} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["exito" => false, "mensaje" => "Error en el servidor: " . $e->getMessage()]);
}
?>