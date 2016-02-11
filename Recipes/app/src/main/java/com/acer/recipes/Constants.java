package com.acer.recipes;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Constants {
    //public static final int PORT = 8888;
    public static final String SERVER_ADDRESS = "http://192.168.1.35/";
    public static final String GET_PRODUCTS_ADDRESS = SERVER_ADDRESS + "get_all_products.php";
    public static final String GET_RECIPES_ADDRESS = SERVER_ADDRESS + "get_recipes.php";
    public static final String GET_RECIPES_BY_CCAL_ADDRESS = SERVER_ADDRESS + "get_recipes_by_ccal.php";

    public static final Map<String, Integer> COMMANDS = new HashMap<String, Integer>();
    public static DbOpenHelper dbHelper;

}
