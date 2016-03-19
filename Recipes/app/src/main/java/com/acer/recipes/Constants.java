package com.acer.recipes;

import com.acer.recipes.Helpers.DbHelper;

public class Constants {
    public static final String SERVER_ADDRESS = "https://api.edamam.com/search?";
    private static final String AppId_App_key = "&app_id=71ced4a7&app_key=504976f01085e918bbc08f7a1b5e2f59";
    public static final String GET_RECIPES_ADDRESS = SERVER_ADDRESS + AppId_App_key + "&q=";
    public static final String GET_RECIPES_BY_CCAL_ADDRESS = SERVER_ADDRESS + AppId_App_key + "&calories=gte%200,lte%20";

    public static DbHelper dbHelper;

}
