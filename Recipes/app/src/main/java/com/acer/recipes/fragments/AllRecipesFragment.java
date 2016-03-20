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

public class AllRecipesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    String inputFromServer = new String();

    public static final Constants myConst = new Constants();
    MyTask myTask;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();

    RVAdapter adapter;
    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        adapter = new RVAdapter(getContext(), getActivity(), recipeArrayList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        myTask = new MyTask();
        myTask.execute();
        return view;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "Обновление началось", Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(true);
        // ждем 3 секунды и прячем прогресс
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                myTask = new MyTask();
                myTask.execute();
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Обновление завершено", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
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
                URL fullUrl = new URL(myConst.GET_ALL_RECIPES);
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

            view.findViewById(R.id.loading_panel).setVisibility(View.GONE);

            rv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}

