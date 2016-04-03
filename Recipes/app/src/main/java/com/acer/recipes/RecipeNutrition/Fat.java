package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Fat implements Serializable {
    private int totalWeight;
    private int totalPerRecipe;
    private int dailyPerRecipe;
    private int saturatedPerRecipe;
    private int transPerRecipe;
    private int monoPerRecipe;
    private int polyPerRecipe;

    public Fat(){}

    public Fat (int _totalWeight, int _total, int _daily, int _saturated, int _trans, int _monounsaturated, int _polyunsaturated)
    {
        totalWeight = _totalWeight;
        totalPerRecipe = _total;
        dailyPerRecipe = _daily;
        saturatedPerRecipe = _saturated;
        transPerRecipe = _trans;
        monoPerRecipe = _monounsaturated;
        polyPerRecipe = _polyunsaturated;
    }

    public int getTotalWeight() {return totalWeight;}
    public int getTotalPerRecipe() {return totalPerRecipe;}
    public int getDailyPerRecipe() {return dailyPerRecipe;}
    public int getSaturatedPerRecipe() {return saturatedPerRecipe;}
    public int getTransPerRecipe() {return transPerRecipe;}
    public int getMonoPerRecipe() {return monoPerRecipe;}
    public int getPolyPerRecipe() {return polyPerRecipe;}

    public int getTotalPer100g() {
        double totalTemp = (double) totalPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(totalTemp);
    }

    public int getDailyPer100g() {
        double dailyTemp = (double) dailyPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(dailyTemp);
    }

    public int getSaturatedPer100g() {
        double saturatedTemp = (double) saturatedPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(saturatedTemp);
    }

    public int getTransPer100g() {
        double transTemp = (double) transPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(transTemp);
    }

    public int getMonoPer100g() {
        double monounsaturatedTemp = (double) monoPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(monounsaturatedTemp);
    }

    public int getPolyPer100g() {
        double polyunsaturatedTemp = (double) polyPerRecipe / (double) totalWeight * 100;
        return (int) Math.round(polyunsaturatedTemp);
    }



}