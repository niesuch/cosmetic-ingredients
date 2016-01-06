<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if (isset($_GET["id"])) {
    $id = $_GET['id'];

    $result = mysql_query("SELECT * FROM ingredients WHERE id = $id");

    if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $ingredient = array();
            $ingredient["id"] = $result["id"];
            $ingredient["name"] = $result["name"];
            $ingredient["desc"] = $result["desc"];

            $response["success"] = 1;

            $response["ingredients"] = array();

            array_push($response["ingredients"], $ingredient);

            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "Ingredient not found";

            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Ingredient not found";

        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "You must give id";

    echo json_encode($response);
}
?>