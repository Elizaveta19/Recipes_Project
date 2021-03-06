package com.acer.recipes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acer.recipes.Constants;
import com.acer.recipes.Helpers.DbHelper;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    RVAdapter adapter;
    RecyclerView rv;

    public static final Constants myConst = new Constants();

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        myConst.dbHelper = new DbHelper(getContext());
        recipeArrayList = myConst.dbHelper.getAllRecipes();

        view.findViewById(R.id.loading_panel).setVisibility(View.GONE);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(getContext(), getActivity(), recipeArrayList);
        rv.setAdapter(adapter);

        return view;
    }

    public static FavoritesFragment getFragment() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);

        return fragment;
    }
}