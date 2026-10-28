<?php
header('content-Type: application/json; charset=utf-8'); 
require_once'conexion.php'; 

$idcarrera = $_GET['id_carrera']?? null;

$sql = "SELECT c.id_curso, c.nombre_curso, c.cupo, c.url_imagen_principal, ca.nombre_carrera, COUNT(i.id_inscripcion) AS inscritos FROM cursos c INNER JOIN carreras ca ON ca.id_carrera = c.id_carrera LEFT JOIN inscripciones i ON i.id_curso = c.id_curso";
if($idcarrera !== null && $idcarrera != 0){ 
    $sql .=" WHERE c.id_carrera = ?";
}
$sql .=" GROUP BY c.id_curso";

$stmt = $pdo->prepare($sql);
if ($idcarrera !== null && $idcarrera != 0){ 
    $stmt->execute([$idcarrera]);
}else {
    $stmt->execute();
}

$cursos = [];
$total =0;

while ($fila=$stmt->fetch()){
    $fila['id_curso'] = (int)$fila['id_curso'];
    $fila['cupo'] = (int)$fila['cupo'];
    $fila['inscritos'] = (int)$fila['inscritos'];
    $total +=$fila ['inscritos'];
    $cursos[] =$fila;
}

echo json_encode([
    "total"=>$total,
    "cursos"=>$cursos
]);
