package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Carbs implements Serializable {
    private int totalWeight;
    private int totalPerRecipe;
    private int dailyPerRecipe;
    private int carbsNetPerRecipe;
    private int fiberPerRecipe;
    private int sugarsPerRecipe;

    public Carbs(){}

    public Carbs (int _totalWeight, int _total, int _daily, int _carbsNet, int _fiber, int _sugars)
    {
        totalWeight = _totalWeight;
        totalPerRecipe = _total;
        dailyPerRecipe = _daily;
        carbsNetPerRecipe = _carbsNet;
        fiberPerRecipe = _fiber;
        sugarsPerRecipe = _sugars;
    }

    public int getTotalWeight() {return totalWeight;}
    public int getTotalPerRecipe() {return totalPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}
    public int getCarbsNetPerRecipe() {return carbsNetPerRecipe;}
    public int getFiberPerRecipe() {return fiberPerRecipe;}
    public int getSugarsPerRecipe() {return sugarsPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(totalTemp);
    }
    public int getDailyPer100g() {
        double dailyTemp = (double) getDailyPerRecipe() / (double) totalWeight * 100;
        return (int) Math.round(dailyTemp);
    }
    public int getCarbsNetPer100g() {
        double carbsNetTemp = (double) carbsNetPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(carbsNetTemp);
    }
    public int getFilberPer100g() {
        double filberTemp = (double) fiberPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(filberTemp);
    }
    public int getSugarsPer100g() {
        double sugarsTemp = (double) sugarsPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(sugarsTemp);
    }

}