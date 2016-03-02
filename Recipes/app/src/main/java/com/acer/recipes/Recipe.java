package com.acer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

import com.acer.recipes.RecipeNutrition.Carbs;
import com.acer.recipes.RecipeNutrition.Fat;
import com.acer.recipes.RecipeNutrition.Protein;

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

    private Fat fat;
    private Carbs carbs;
    private Protein protein;


    public Recipe(int _id, String _title, ArrayList<String> _ingredients, String _recipeUrl, int _calories, int _totalWeight, String _imgUrl, Fat _fat, Carbs _carbs, Protein _protein)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        recipeUrl = _recipeUrl;
        totalWeight = _totalWeight;
        imgUrl = _imgUrl;

        double caloriesTemp = (double) _calories / (double) _totalWeight * 100;
        calories = (int) Math.round(caloriesTemp);

        fat = _fat;
        carbs = _carbs;
        protein = _protein;
    }

    public int getId() { return id; }

    public String getTitle(){ return title; }

    public ArrayList<String> getIngredients() { return ingredients; }

    public String getUrl() { return recipeUrl;  }

    public int getCalories(){ return calories; }

    public int getTotalWeight() { return totalWeight; }

    public String getImgUrl() { return imgUrl; }

    public Fat getFat() { return fat;}
    public Carbs getCarbs() { return carbs; }
    public Protein getProtein() { return protein; }
}