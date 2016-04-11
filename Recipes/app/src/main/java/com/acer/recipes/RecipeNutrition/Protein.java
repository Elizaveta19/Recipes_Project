package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Protein implements Serializable {
    private int totalRecipeWeight;
    private int totalProteinsPerRecipe;
    int dailyPerRecipe;

    public Protein() {}

    public Protein (int _totalWeight, int _total, int _daily)
    {
        totalRecipeWeight = _totalWeight;
        totalProteinsPerRecipe = _total;
        dailyPerRecipe = _daily;
    }

    public int getTotalRecipeWeight() {return totalRecipeWeight;}
    public int getTotalProteinsPerRecipe() {return totalProteinsPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalProteinsPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(totalTemp);
    }
    public int getDailyPer100g() {
        double dailyTemp = (double) getDailyPerRecipe() / (double) totalRecipeWeight * 100;
        return (int) Math.round(dailyTemp);
    }
}