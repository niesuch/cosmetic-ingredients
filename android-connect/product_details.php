<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if (isset($_GET["id"])) {
    $id = $_GET['id'];

    $result = mysql_query("SELECT p.name AS name, pk.qr_code AS qr_code, p.id AS id, s.name AS ingredient_name, s.desc AS ingredient_desc, s.id AS id_ingredient FROM products AS p 
	LEFT JOIN products_ingredient AS ps ON ps.id_product = p.id 
	LEFT JOIN ingredients AS s ON s.id = ps.id_ingredient
	LEFT JOIN products_qr_code AS pk ON pk.id = p.id
	WHERE p.id = $id");

    if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {
			$response["products"] = array();
			
			while ($row = mysql_fetch_array($result)) {
				$product = array();
				$product["id"] = $row["id"];
				$product["name"] = $row["name"];
				$product["qr_code"] = $row["qr_code"];
				$product["ingredient_name"] = $row["ingredient_name"];
				$product["ingredient_desc"] = $row["ingredient_desc"];
				$product["id_ingredient"] = $row["id_ingredient"];
				array_push($response["products"], $product);
			}

            $response["success"] = 1;

            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "Nothing found";

            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Product not found";

        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "You must give id";

    echo json_encode($response);
}
?>
