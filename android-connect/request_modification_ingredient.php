<?php
header('Content-type: application/json');
$response = array();

require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['id_ingredient'])) {
    $id_ingredient = $_POST['id_ingredient'];
    $user_desc = (isset($_POST['user_desc'])) ? $_POST['user_desc'] : NULL;
	
    $result = mysql_query("INSERT INTO reports_modifications(id_ingredient, user_desc, status, date_register_notification) VALUES('$id_ingredient', '$user_desc', 0, NOW())");

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