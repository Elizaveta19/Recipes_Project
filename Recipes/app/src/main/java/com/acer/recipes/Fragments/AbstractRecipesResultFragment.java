package com.acer.recipes.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acer.recipes.Constants;
import com.acer.recipes.Helpers.JsonManager;
import com.acer.recipes.R;
import com.acer.recipes.RVAdapter;
import com.acer.recipes.Recipe;

import java.net.URL;
import java.util.ArrayList;

public abstract class AbstractRecipesResultFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int LAYOUT = R.layout.recipes_result;
    private View view;

    String inputFromServer = new String();
    String outToServer = new String();
    String outToServerFromTo = new String();
    String outToServerWithLabels = new String();
    RVAdapter adapter;
    RecyclerView rv;
    String query = "";
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static final Constants myConst = new Constants();
    MyTask myTask;
    LinearLayoutManager llm;
    int positionStart = 0;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        initializeFragmentFields();
        Log.v("Out to server=========", Constants.DIET_FILTER);
        outToServerWithLabels = outToServer + Constants.DIET_FILTER + Constants.HEALTH_FILTER;
        outToServerFromTo = outToServerWithLabels;

        rv = (RecyclerView) view.findViewById(R.id.rv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        adapter = new RVAdapter(getContext(), getActivity(), recipeArrayList);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged (RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);

                int lastVisiblePosition = llm.findLastVisibleItemPosition();
                int itemCount = llm.getItemCount();
                int visibleCount = llm.getChildCount();
                if((lastVisiblePosition == itemCount-1)&& (visibleCount < 3)){
                    try {
                        outToServerFromTo = outToServerWithLabels + "&from=" + (recipeArrayList.size()+1) + "&to=" + (recipeArrayList.size() + 11);
                        positionStart = recipeArrayList.size();
                        myTask = new MyTask();
                        myTask.execute();
                    } catch (Exception e) {
                        for (StackTraceElement ste : e.getStackTrace())
                            Log.v("Ошибка============", ste.toString());
                    }
                }
            }
        });

        myTask = new MyTask();
        myTask.execute();
        return view;
    }

    @Override
    public void onRefresh() {
        outToServerWithLabels = outToServer + Constants.DIET_FILTER + Constants.HEALTH_FILTER;
        outToServerFromTo = outToServerWithLabels;
        recipeArrayList.clear();
        adapter.notifyDataSetChanged();
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

    public abstract void initializeFragmentFields();

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            JsonManager jsonManager = new JsonManager();
            try {
                outToServerWithLabels = outToServer + Constants.DIET_FILTER + Constants.HEALTH_FILTER;
                URL fullUrl = new URL(outToServerFromTo);
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
            adapter.notifyItemRangeInserted(positionStart, 10);
            super.onPostExecute(result);
        }
    }
}