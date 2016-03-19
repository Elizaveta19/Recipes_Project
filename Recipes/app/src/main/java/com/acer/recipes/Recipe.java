package com.acer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

import com.acer.recipes.RecipeNutrition.Carbs;
import com.acer.recipes.RecipeNutrition.Fat;
import com.acer.recipes.RecipeNutrition.Protein;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe  implements Serializable{
    private String id;
    private String title;
    private ArrayList<String> ingredients;
    private String sourceUrl;
    private int calories;
    private int totalWeight;
    private String imgUrl;

    private Fat fat;
    private Carbs carbs;
    private Protein protein;

    private boolean favorite;


    public Recipe(String _id, String _title, ArrayList<String> _ingredients, String _sourceUrl, int _calories, int _totalWeight, String _imgUrl, Fat _fat, Carbs _carbs, Protein _protein, boolean _isFavorite)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        sourceUrl = _sourceUrl;
        totalWeight = _totalWeight;
        imgUrl = _imgUrl;

        //double caloriesTemp = (double) _calories / (double) _totalWeight * 100;
        //calories = (int) Math.round(caloriesTemp);
        calories = _calories;

        fat = _fat;
        carbs = _carbs;
        protein = _protein;

        favorite = _isFavorite;
    }

    public String getId() { return id; }

    public String getTitle(){ return title; }

    public ArrayList<String> getIngredients() { return ingredients; }

    public String getUrl() { return sourceUrl;  }

    public int getCalories(){
        return calories; }

    public int getStandartCalories(){
        double caloriesTemp = (double) calories / (double) totalWeight * 100;
        int standartCalories = (int) Math.round(caloriesTemp);
        return standartCalories; }

    public int getTotalWeight() { return totalWeight; }

    public String getImgUrl() { return imgUrl; }

    public Fat getFat() { return fat;}
    public Carbs getCarbs() { return carbs; }
    public Protein getProtein() { return protein; }

    public void setFavorite() {
        favorite = true;
    }

    public void unsetFavorite() {
        favorite = false;
    }

    public boolean isFavorite() {
        return favorite;
    }
}