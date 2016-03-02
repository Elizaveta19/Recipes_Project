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

import com.acer.recipes.Constants;
import com.acer.recipes.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;


import java.net.URL;
import java.util.ArrayList;

public class RecipesResultFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    String inputFromServer = new String();
    String outToServer = new String();
    RVAdapter adapter;
    RecyclerView rv;
    String query = "";
    String maxCalories = "";

    public static final Constants myConst = new Constants();
    MyTask myTask;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        maxCalories = String.valueOf(bundle.getInt("maxCalories"));

        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(getContext(), getActivity(), recipeArrayList);

        myTask = new MyTask();
        myTask.execute();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*Recipe[] recipeArray = new Recipe[recipeArrayList.size()];
        Recipe recipe = recipeArrayList.get(position);
        Fragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", recipe);
        fragment.setArguments(args);

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
        }*/
    }

    public static RecipesResultFragment getFragment() {
        Bundle args = new Bundle();
        RecipesResultFragment fragment = new RecipesResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            JsonManager jsonManager = new JsonManager();
            try {
                // + "&calories=lte%" + maxCalories
                URL fullUrl = new URL(myConst.GET_RECIPES_ADDRESS + query + "&app_id=71ced4a7&app_key=504976f01085e918bbc08f7a1b5e2f59");
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
            rv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}

