package com.acer.recipes.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acer.recipes.Constants;
import com.acer.recipes.Helpers.JsonManager;
import com.acer.recipes.MainActivity;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;


import java.net.URL;
import java.util.ArrayList;

public class RecipesResultFragment extends AbstractRecipesResultFragment {

    @Override
    public void setFragmentFields() {
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        outToServer = myConst.GET_RECIPES_ADDRESS + query;
        outToServerFromTo = outToServer;
    }

    public static RecipesResultFragment getFragment()  {
        Bundle args = new Bundle();
        RecipesResultFragment fragment = new RecipesResultFragment();
        fragment.setArguments(args);

        return fragment;
    }
}

