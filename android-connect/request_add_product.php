<?php
header('Content-type: application/json');
$response = array();

if (isset($_POST['name'])) {
    $name = $_POST['name'];

    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("INSERT INTO reports_suggestions_products(name, status, date_register_notification) VALUES('$name', 0, NOW())");

    if ($result) {
        $response["success"] = 1;
        $response["message"] = "Success";

        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Oops! Something wrong!";
        
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "All fields must fill!";

    echo json_encode($response);
}
?>
