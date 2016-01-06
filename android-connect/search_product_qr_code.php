<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if (isset($_GET["qr_code"])) {
    $qr_code = $_GET['qr_code'];

	$result = mysql_query("SELECT p.id AS id, p.name AS name, pk.qr_code AS qr_code FROM products AS p LEFT JOIN products_qr_code AS pk ON p.id = pk.id WHERE pk.qr_code LIKE '$qr_code'") or die(mysql_error());

	if (mysql_num_rows($result) > 0) {
		$response["products"] = array();
    
		while ($row = mysql_fetch_array($result)) {
			$product = array();
			$product["id"] = $row["id"];
			$product["name"] = $row["name"];
			$product["qr_code"] = $row['qr_code'];

			array_push($response["products"], $product);
		}
		$response["success"] = 1;

		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Nothing has been found";

		echo json_encode($response);
	}
} else {
	$response["success"] = 0;
    $response["message"] = "You must get search criterion";

    echo json_encode($response);
}
?>
