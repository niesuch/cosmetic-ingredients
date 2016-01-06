<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();

if(isset($_GET['name'])) {
	$name = $_GET['name'];
    $result = mysql_query("SELECT * FROM ingredients WHERE name LIKE '%$name%' ORDER BY name") or die(mysql_error());   
}
else {
	$result = mysql_query("SELECT * FROM ingredients ORDER BY name") or die(mysql_error());
}
if (mysql_num_rows($result) > 0) {
	$response["ingredients"] = array();
    
	while ($row = mysql_fetch_array($result)) {
		$ingredient = array();
		$ingredient["id"] = $row["id"];
		$ingredient["name"] = $row["name"];
		$ingredient["desc"] = $row["desc"];

		array_push($response["ingredients"], $ingredient);
	}
	$response["success"] = 1;

	echo json_encode($response);
} else {
	$response["success"] = 0;
	$response["message"] = "Nothing has been found";

	echo json_encode($response);
}
?>
