package com.acer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe  implements Serializable{
    private int id;
    private String title;
    private ArrayList<String> ingredients;
    private String recipeUrl;
    private int calories;
    private int totalWeight;
    private String imgUrl;

    public Recipe(int _id, String _title, ArrayList<String> _ingredients, String _recipeUrl, int _calories, int _totalWeight, String _imgUrl)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        recipeUrl = _recipeUrl;
        calories = _calories;
        totalWeight = _totalWeight;
        imgUrl = _imgUrl;
    }

    public int getId() { return id; }

    public String getTitle(){ return title; }

    public ArrayList<String> getIngredients() { return ingredients; }

    public String getUrl() { return recipeUrl;  }

    public int getTotalCalories(){ return calories; }

    public int getStandartCalories(){ return calories / totalWeight * 100; }

    public int getTotalWeight() { return totalWeight; }

    public String getImgUrl() { return imgUrl; }
}