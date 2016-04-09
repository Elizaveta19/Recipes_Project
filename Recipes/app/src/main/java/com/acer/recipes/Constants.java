package com.acer.recipes;

import com.acer.recipes.Helpers.DbHelper;

import java.util.ArrayList;

public class Constants {
    public static final String SERVER_ADDRESS = "https://api.edamam.com/search?";
    private static final String AppId_App_key = "app_id=1d3a2afe&app_key=defdbe20f0e90af634a2441b451a7818";
    public static final String GET_RECIPES_ADDRESS = SERVER_ADDRESS + AppId_App_key + "&q=";
    public static final String GET_ALL_RECIPES = SERVER_ADDRESS + AppId_App_key + "&q=all";
    public static final String PREFIX_CCAL_ADDRESS = "&calories=gte%200,%20lte%20";

    public static String DIET_FILTER = "";
    public static String HEALTH_FILTER = "";

    public static DbHelper dbHelper;

}
