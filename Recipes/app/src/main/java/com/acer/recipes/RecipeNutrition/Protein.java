package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Protein implements Serializable {
    int total;
    int daily;

    public Protein (int _totalWeight, int _total, int _daily)
    {
        double totalTemp = (double) _total / (double) _totalWeight * 100;
        total = (int) Math.round(totalTemp);

        double dailyTemp = (double) _daily / (double) _totalWeight * 100;
        daily = (int) Math.round(dailyTemp);
    }

    public int getTotal () { return total;}
    public int getDaily () {return daily; }
}