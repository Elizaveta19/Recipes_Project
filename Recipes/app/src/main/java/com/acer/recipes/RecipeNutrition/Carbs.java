package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Carbs implements Serializable {
    private int totalRecipeWeight;
    private int totalCarbsPerRecipe;
    private int dailyPerRecipe;
    private int carbsNetPerRecipe;
    private int fiberPerRecipe;
    private int sugarsPerRecipe;

    public Carbs(){}

    public Carbs (int _totalRecipeWeight, int _totalCarbsPerRecipe, int _daily, int _carbsNet, int _fiber, int _sugars)
    {
        totalRecipeWeight = _totalRecipeWeight;
        totalCarbsPerRecipe = _totalCarbsPerRecipe;
        dailyPerRecipe = _daily;
        carbsNetPerRecipe = _carbsNet;
        fiberPerRecipe = _fiber;
        sugarsPerRecipe = _sugars;
    }

    public int getTotalRecipeWeight() {return totalRecipeWeight;}
    public int getTotalCarbsPerRecipe() {return totalCarbsPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}
    public int getCarbsNetPerRecipe() {return carbsNetPerRecipe;}
    public int getFiberPerRecipe() {return fiberPerRecipe;}
    public int getSugarsPerRecipe() {return sugarsPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalCarbsPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(totalTemp);
    }
    public int getDailyPer100g() {
        double dailyTemp = (double) getDailyPerRecipe() / (double) totalRecipeWeight * 100;
        return (int) Math.round(dailyTemp);
    }
    public int getCarbsNetPer100g() {
        double carbsNetTemp = (double) carbsNetPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(carbsNetTemp);
    }
    public int getFilberPer100g() {
        double filberTemp = (double) fiberPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(filberTemp);
    }
    public int getSugarsPer100g() {
        double sugarsTemp = (double) sugarsPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(sugarsTemp);
    }

}