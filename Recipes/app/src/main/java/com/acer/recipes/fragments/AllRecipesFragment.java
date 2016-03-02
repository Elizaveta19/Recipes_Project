package com.acer.recipes.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.acer.recipes.Constants;
import com.acer.recipes.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AllRecipesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    String comment = new String();
    String inputFromServer = new String();

    public static final Constants myConst = new Constants();
    MyTask myTask;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    ListView listView;

    private ArrayList<HashMap<String, Object>> myRecipes;
    private static final String RECIPE_TITLE = "title";    // Главное название, большими буквами
    private static final String TIME = "time";  // Наименование, то что ниже главного
    private static final String CCAL = "ccal";  // Наименование, то что ниже главного

    RVAdapter adapter;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

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

        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arr_backButton: {
                Fragment fragment = SearchRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;
            }
            default:
                break;
        }
    }

    public static AllRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        AllRecipesFragment fragment = new AllRecipesFragment();
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
                URL fullUrl = new URL(myConst.GET_RECIPES_ADDRESS);
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

