<?php
 
$response = array();
 
require_once dirname(__FILE__) . './DbConnect.php';
 
//echo phpversion(); 
$db = new DB_CONNECT();
get_details();

function get_details()
{
	echo $argv[0];
	$_GET["product_id"] = 3;
	if (isset($_GET["product_id"])) {
		$product_id = $_GET["product_id"];
		
	 
		$result = mysql_query("SELECT * FROM products WHERE product_id='$product_id'");
	 
		if (!empty($result)) {
			if (mysql_num_rows($result) > 0) {
	 
				$res = mysql_fetch_array($result);

				$product = array();
				$product['product_id'] = $res['product_id'];
				$product['name'] = $res['name'];
				$product['ccal'] = $res['ccal'];
				$response["success"] = 1;

				$response["product"] = array();		
				array_push($response["product"], $product);
				
	 
				echo json_encode($response, JSON_UNESCAPED_UNICODE);		
					
			} else {
				$response["success"] = 0;
				$response["message"] = "No product found";
	 
				echo json_encode($response);
			}
		} else {
			$response["success"] = 0;
			$response["message"] = "No product found";
	 
			echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
	 
		echo json_encode($response);
	}
	
	return $response;
	
	
}
?>

