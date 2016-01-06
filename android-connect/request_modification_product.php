<?php
header('Content-type: application/json');
$response = array();

if (isset($_POST['id_product'])) {
    $id_product = $_POST['id_product'];
    $user_desc = (isset($_POST['user_desc'])) ? $_POST['user_desc'] : NULL;

    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("INSERT INTO reports_modifications_products(id_product, user_desc, status, date_register_notification) VALUES('$id_product', '$user_desc', 0, NOW())");

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
