<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();
if(isset($_GET['name'])) {
    $result = mysql_query("SELECT * FROM products WHERE name LIKE '%".$_GET['name']."%' ORDER BY name") or die(mysql_error());   
}
else {
    $result = mysql_query("SELECT * FROM products ORDER BY name") or die(mysql_error());
}
if (mysql_num_rows($result) > 0) {
    $response["products"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        $product = array();
        $product["pid"] = $row["id"];
        $product["name"] = $row["name"];

        array_push($response["products"], $product);
    }
    $response["success"] = 1;

    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Nothing has been found";

    echo json_encode($response);
}
?>
