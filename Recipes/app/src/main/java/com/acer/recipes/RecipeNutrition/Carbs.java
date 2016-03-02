package com.acer.recipes.RecipeNutrition;

import java.io.Serializable;

public class Carbs implements Serializable {
    int total;
    int daily;
    int carbsNet;
    int filber;
    int sugars;

    public Carbs (int _totalWeight, int _total, int _daily, int _carbsNet, int _filber, int _sugars)
    {
        double totalTemp = (double) _total / (double) _totalWeight * 100;
        total = (int) Math.round(totalTemp);

        double dailyTemp = (double) _daily / (double) _totalWeight * 100;
        daily = (int) Math.round(dailyTemp);

        double carbsNetTemp = (double) _carbsNet / (double) _totalWeight * 100;
        carbsNet = (int) Math.round(carbsNetTemp);

        double filberTemp = (double) _filber / (double) _totalWeight * 100;
        filber = (int) Math.round(filberTemp);

        double sugarsTemp = (double) _sugars / (double) _totalWeight * 100;
        sugars = (int) Math.round(sugarsTemp);
    }

    public int getTotal() {return total;}
    public int getDaily() {return daily;}
    public int getCarbsNet() {return carbsNet;}
    public int getFilber() {return filber;}
    public int getSugars() {return sugars;}

}