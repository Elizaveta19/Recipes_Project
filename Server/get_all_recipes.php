<?php

function get_all_recipes()
{
	$response = array();
	$response_1 = "";
	require_once dirname(__FILE__) . './DbConnect.php';

	//echo phpversion(); 
	$db = new DB_CONNECT();
	$result = array();
	$result = mysql_query("SELECT recipes.id_recipe, recipes.name as title, recipes.text, recipes.ingredients, recipes.ccal, recipes.time, products.id_product, products.name
							FROM recipes
							INNER JOIN products_in_recipes as p_i_r
							INNER JOIN products
							ON (recipes.id_recipe = p_i_r.id_recipe AND p_i_r.id_product = products.id_product)
							GROUP BY recipes.id_recipe");

	if (!empty($result)) {
		if (mysql_num_rows($result) > 0) {

			$response["recipe"] = array();
			while ($res=mysql_fetch_array($result))
			{
				$recipe['id_recipe'] = $res['id_recipe'];
				$recipe['title'] = $res['title'];
				$recipe['ingredients'] = $res['ingredients'];
				$recipe['text'] = $res['text'];
				$recipe['ccal'] = $res['ccal'];
				$recipe['time'] = $res['time'];
				$response["success"] = 1;

				//$response["recipe"] = array();
				array_push($response["recipe"], $recipe);
			}
			$response["success"] = 1;
			$response_1 = json_encode($response, JSON_UNESCAPED_UNICODE);

		} else {
			$response["success"] = 0;
			$response["message"] = "No recipe found";

			echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "No recipe found";

		echo json_encode($response);
	}
	
	return $response_1;
	
}
?>

