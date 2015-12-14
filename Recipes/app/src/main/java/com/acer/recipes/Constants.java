package com.acer.recipes;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Constants {
    public static final int PORT = 8888;
    public static final String SERVER_ADDRESS = "192.168.1.35";

    public static final Map<String, Integer> COMMANDS = new HashMap<String, Integer>();
    public static DbOpenHelper dbHelper;

    public Constants()
    {
        COMMANDS.put("getAllProducts", 1);
        COMMANDS.put("getRecipes", 2);
        COMMANDS.put("getRecipesByCcal", 3);
        COMMANDS.put("getAllRecipes", 4);
    }
}
