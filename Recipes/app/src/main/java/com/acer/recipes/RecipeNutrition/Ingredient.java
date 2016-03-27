package com.acer.recipes.RecipeNutrition;

public class Ingredient {
    private int id;
    private boolean isBought;
    private String title;

    public Ingredient(String _title, int _isBought){
        isBought = "1".equals(String.valueOf(_isBought));
        title = _title;
    }

    public Ingredient(int _id, String _title, int _isBought){
        id = _id;
        isBought = "1".equals(String.valueOf(_isBought));
        title = _title;
    }

    public int getId(){
        return id;
    }

    public boolean getIsBought(){
        return isBought;
    }

    public String getTitle(){
        return title;
    }
}
