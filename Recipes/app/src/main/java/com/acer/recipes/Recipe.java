package com.acer.recipes;

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
    private int yield;
    private String imgUrl;

    private Fat fat;
    private Carbs carbs;
    private Protein protein;

    private boolean favorite;


    public Recipe(String _id, String _title, ArrayList<String> _ingredients, String _sourceUrl, int _calories, int _totalWeight, int _yield, String _imgUrl, Fat _fat, Carbs _carbs, Protein _protein, boolean _isFavorite)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        sourceUrl = _sourceUrl;
        totalWeight = _totalWeight;
        yield = _yield;
        imgUrl = _imgUrl;

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

    public int getCaloriesProPerson(){
        return calories / yield; }

    public int getGrammProPerson(){
        return totalWeight / yield; }

    public int getTotalWeight() { return totalWeight; }

    public int getNumberOfPortion() { return yield; }

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