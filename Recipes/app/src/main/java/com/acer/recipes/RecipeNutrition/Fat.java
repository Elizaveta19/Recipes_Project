package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Fat implements Serializable {
    int total;
    int daily;
    int saturated;
    int trans;
    int monounsaturated;
    int polyunsaturated;

    public Fat(){}

    public Fat (int _totalWeight, int _total, int _daily, int _saturated, int _trans, int _monounsaturated, int _polyunsaturated)
    {
        double totalTemp = (double) _total / (double) _totalWeight * 100;
        total = (int) Math.round(totalTemp);

        double dailyTemp = (double) _daily / (double) _totalWeight * 100;
        daily = (int) Math.round(dailyTemp);

        double saturatedTemp = (double) _saturated / (double) _totalWeight * 100;
        saturated = (int) Math.round(saturatedTemp);

        double transTemp = (double) _trans / (double) _totalWeight * 100;
        trans = (int) Math.round(transTemp);

        double monounsaturatedTemp = (double) _monounsaturated / (double) _totalWeight * 100;
        monounsaturated = (int) Math.round(monounsaturatedTemp);

        double polyunsaturatedTemp = (double) _polyunsaturated / (double) _totalWeight * 100;
        polyunsaturated = (int) Math.round(polyunsaturatedTemp);
    }

    public int getTotal() {return total;}
    public int getDaily() {return daily;}
    public int getSaturated() {return saturated;}
    public int getTrans() {return trans;}
    public int getMonounsaturated() {return monounsaturated;}
    public int getPolyunsaturated() {return polyunsaturated;}

}