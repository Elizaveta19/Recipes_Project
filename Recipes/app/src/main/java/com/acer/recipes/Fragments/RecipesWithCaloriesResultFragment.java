package com.acer.recipes.Fragments;

import android.os.Bundle;

public class RecipesWithCaloriesResultFragment extends AbstractRecipesResultFragment {

    String maxCalories = "";

    @Override
    public void setFragmentFields() {
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        maxCalories = String.valueOf(bundle.getInt("maxCalories"));
        if (query != null){
            outToServer = myConst.GET_RECIPES_ADDRESS + query;
        }
        else {
            outToServer = myConst.GET_RECIPES_ADDRESS + "all";
        }
        if (maxCalories != "0"){
            outToServer += myConst.PREFIX_CCAL_ADDRESS + maxCalories;
        }
    }

    public static RecipesWithCaloriesResultFragment getFragment() {
        Bundle args = new Bundle();
        RecipesWithCaloriesResultFragment fragment = new RecipesWithCaloriesResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

