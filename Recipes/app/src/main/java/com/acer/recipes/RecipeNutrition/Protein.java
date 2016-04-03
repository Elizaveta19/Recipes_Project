package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Protein implements Serializable {
    private int totalWeight;
    private int totalPerRecipe;
    int dailyPerRecipe;

    public Protein() {}

    public Protein (int _totalWeight, int _total, int _daily)
    {
        totalWeight = _totalWeight;
        totalPerRecipe = _total;
        dailyPerRecipe = _daily;
    }

    public int getTotalWeight() {return totalWeight;}
    public int getTotalPerRecipe() {return totalPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(totalTemp);
    }
    public int getDailyPer100g() {
        double dailyTemp = (double) getDailyPerRecipe() / (double) totalWeight * 100;
        return (int) Math.round(dailyTemp);
    }
}