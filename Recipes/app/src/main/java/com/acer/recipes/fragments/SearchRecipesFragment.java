package com.acer.recipes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.acer.recipes.R;
import com.acer.recipes.RecipesResult;


public class SearchRecipesFragment extends Fragment implements View.OnClickListener{

    private static final int LAYOUT = R.layout.activity_search_recipes;
    private View view;
    Button sa_searchButton;
    Button sa_allProductsButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        sa_searchButton= (Button) view.findViewById(R.id.sa_searchButton);
        sa_allProductsButton= (Button) view.findViewById(R.id.sa_allProductsButton);
        sa_searchButton.setOnClickListener(this);
        sa_allProductsButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sa_searchButton:
                Intent intent = new Intent(getActivity(), RecipesResult.class);
                startActivity(intent);
                break;
            case R.id.sa_allProductsButton:
                //new RequestTask().execute("http://myhomepage.hol.es/login.php");
                break;
        }
    }

    public static SearchRecipesFragment getFragment()
    {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
