<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php"


$res = $db_connexion->query('SELECT * FROM Categorie', PDO::FETCH_OBJ);

echo json_encode($res->fetchAll());