package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Fat implements Serializable {
    private int totalRecipeWeight;
    private int totalFatPerRecipe;
    private int dailyPerRecipe;
    private int saturatedPerRecipe;
    private int transPerRecipe;
    private int monoPerRecipe;
    private int polyPerRecipe;

    public Fat(){}

    public Fat (int _totalRecipeWeight, int _totalFat, int _daily, int _saturated, int _trans, int _monounsaturated, int _polyunsaturated)
    {
        totalRecipeWeight = _totalRecipeWeight;
        totalFatPerRecipe = _totalFat;
        dailyPerRecipe = _daily;
        saturatedPerRecipe = _saturated;
        transPerRecipe = _trans;
        monoPerRecipe = _monounsaturated;
        polyPerRecipe = _polyunsaturated;
    }

    public int getTotalRecipeWeight() {return totalRecipeWeight;}
    public int getTotalFatPerRecipe() {return totalFatPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}
    public int getSaturatedPerRecipe() {return saturatedPerRecipe;}
    public int getTransPerRecipe() {return transPerRecipe;}
    public int getMonoPerRecipe() {return monoPerRecipe;}
    public int getPolyPerRecipe() {return polyPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalFatPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(totalTemp);
    }

    public int getDailyPer100g() {
        double dailyTemp = (double) dailyPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(dailyTemp);
    }

    public int getSaturatedPer100g() {
        double saturatedTemp = (double) saturatedPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(saturatedTemp);
    }

    public int getTransPer100g() {
        double transTemp = (double) transPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(transTemp);
    }

    public int getMonoPer100g() {
        double monounsaturatedTemp = (double) monoPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(monounsaturatedTemp);
    }

    public int getPolyPer100g() {
        double polyunsaturatedTemp = (double) polyPerRecipe / (double) totalRecipeWeight * 100;
        return (int) Math.round(polyunsaturatedTemp);
    }



}