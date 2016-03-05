package com.acer.recipes;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Constants {
    private static final String AppId_AppKey = "app_id=71ced4a7&app_key=504976f01085e918bbc08f7a1b5e2f59";
    public static final String SERVER_ADDRESS = "https://api.edamam.com/search?";
    public static final String GET_RECIPES_ADDRESS = SERVER_ADDRESS + AppId_AppKey + "&q=";
    public static final String GET_RECIPES_BY_CCAL_ADDRESS = SERVER_ADDRESS + AppId_AppKey + "&calories=gte%200,%20lte%20";

    public static DbOpenHelper dbHelper;

}
