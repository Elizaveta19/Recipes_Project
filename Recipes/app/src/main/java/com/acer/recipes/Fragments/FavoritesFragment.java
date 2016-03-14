package com.acer.recipes.Fragments;

import android.database.sqlite.SQLiteDatabase;
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

import com.acer.recipes.Constants;
import com.acer.recipes.DbHelper;
import com.acer.recipes.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.net.URL;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    RVAdapter adapter;
    RecyclerView rv;
    String query = "";

    public static final Constants myConst = new Constants();

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        myConst.dbHelper = new DbHelper(getContext());
        recipeArrayList = myConst.dbHelper.getAllRecipes();


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