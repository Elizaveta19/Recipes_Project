package com.acer.recipes.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.acer.recipes.Constants;
import com.acer.recipes.Helpers.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.net.URL;
import java.util.ArrayList;

public class RecipesResultFragment_2  extends AbstractRecipesResultFragment {

    String maxCalories = "";

    @Override
    public void setFragmentFields() {
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        maxCalories = String.valueOf(bundle.getInt("maxCalories"));
        outToServer = myConst.GET_RECIPES_BY_CCAL_ADDRESS + maxCalories + "&q=" + query;
    }

    public static RecipesResultFragment_2 getFragment() {
        Bundle args = new Bundle();
        RecipesResultFragment_2 fragment = new RecipesResultFragment_2();
        fragment.setArguments(args);

        return fragment;
    }

}

