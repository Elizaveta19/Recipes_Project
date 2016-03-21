package com.acer.recipes.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.acer.recipes.Constants;
import com.acer.recipes.Helpers.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AllRecipesFragment extends AbstractRecipesResultFragment {

    @Override
    public void setFragmentFields() {
        outToServer = myConst.GET_ALL_RECIPES;
    }

    public static AllRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        AllRecipesFragment fragment = new AllRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }
}

