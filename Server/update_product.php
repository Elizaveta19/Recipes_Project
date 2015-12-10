<?php
 
$response = array();
 
if (isset($_POST['product_id']) && isset($_POST['name']) && isset($_POST['ccal'])) {
 
    $product_id = $_POST['product_id'];
    $name = $_POST['name'];
    $ccal = $_POST['ccal'];
 
     require_once dirname(__FILE__) . './DbConnect.php';
 
    $db = new DB_CONNECT();
 
    $result = mysql_query("UPDATE products SET name = '$name', price = '$price' WHERE pid = $product_id");
 
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        echo json_encode($response);
    } else {
 
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    echo json_encode($response);
}
?>