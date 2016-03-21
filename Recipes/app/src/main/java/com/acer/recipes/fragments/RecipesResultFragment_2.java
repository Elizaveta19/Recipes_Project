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

public class RecipesResultFragment_2 extends Fragment {

    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    String comment = new String();
    String inputFromServer = new String();

    public static final Constants myConst = new Constants();
    MyTask myTask;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    String outToServer = new String();
    String query = "";
    String maxCalories = "";

    RVAdapter adapter;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        maxCalories = String.valueOf(bundle.getInt("maxCalories"));
        outToServer = myConst.GET_RECIPES_BY_CCAL_ADDRESS + maxCalories + "&q=" + query;

        view.findViewById(R.id.loading_panel).setVisibility(View.GONE);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(getContext(), getActivity(), recipeArrayList);

        myTask = new MyTask();
        myTask.execute();
        return view;
    }

    public static RecipesResultFragment_2 getFragment() {
        Bundle args = new Bundle();
        RecipesResultFragment_2 fragment = new RecipesResultFragment_2();
        fragment.setArguments(args);

        return fragment;
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {
        String title;
        @Override
        protected Void doInBackground(Void... params) {
            JsonManager jsonManager = new JsonManager();
            try {
                URL fullUrl = new URL(outToServer);
                inputFromServer = jsonManager.getAllRecipes(fullUrl);
                jsonManager.putRecipes(inputFromServer, recipeArrayList);
            } catch (Exception e) {
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            LinearLayout errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
            if (recipeArrayList.isEmpty()){
                errorLayout.setVisibility(View.VISIBLE);
            }
            else {
                errorLayout.setVisibility(View.GONE);
            }

            rv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}

