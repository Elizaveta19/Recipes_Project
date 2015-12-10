<?php

function get_product($id_product)
{
	$response = array();
	$response_1 = "";
	require_once dirname(__FILE__) . './DbConnect.php';

	//echo phpversion(); 
	$db = new DB_CONNECT();

	//echo $argv[0];
	/*$_GET["id_product"] = 3;
	if (isset($_GET["id_product"])) {
		$id_product = $_GET["id_product"];*/
		
	 
		$result = mysql_query("SELECT * FROM products WHERE id_product='$id_product'");
	 
		if (!empty($result)) {
			if (mysql_num_rows($result) > 0) {
	 
				$res = mysql_fetch_array($result);

				//$product = array();
				$product['id_product'] = $res['id_product'];
				$product['name'] = $res['name'];
				$product['ccal'] = $res['ccal'];
				$response["success"] = 1;

				$response["product"] = array();		
				array_push($response["product"], $product);
				
	 
				echo $response_1 = json_encode($response, JSON_UNESCAPED_UNICODE);		
					
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
	/*} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
	 
		echo json_encode($response);*/

	
	return $response_1;
	
}
?>

