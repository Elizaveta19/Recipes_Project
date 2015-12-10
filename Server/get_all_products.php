<?php
 
    $response = array();
    $response_1="";
    require_once dirname(__FILE__) . './DbConnect.php';

    $db = new DB_CONNECT();
function get_all_products()
{
    $result = mysql_query("SELECT * FROM products") or die(mysql_error());

    if (mysql_num_rows($result) > 0) {
        $response["products"] = array();

        while ($row = mysql_fetch_array($result)) {
            $product = array();
            $product["id_product"] = $row["id_product"];
            $product["name"] = $row["name"];
            $product["ccal"] = $row["ccal"];

            array_push($response["products"], $product);
        }
        $response["success"] = 1;

        $response_1 = json_encode($response, JSON_UNESCAPED_UNICODE);
    } else {
        $response["success"] = 0;
        $response["message"] = "No products found";

        echo $response_1 = json_encode($response);
    }

    return $response_1;
}
?>
