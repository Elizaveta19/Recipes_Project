package com.acer.recipes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.acer.recipes.R;

public class StartPageFragment extends Fragment implements View.OnClickListener{

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.start_page;
    private View view;

    Button spa_searchButton;
    TextView allRecipesButton;
    TextView spa_searchButton2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        spa_searchButton = (Button) view.findViewById(R.id.spa_searchButton);
        spa_searchButton.setOnClickListener(this);
        allRecipesButton = (TextView) view.findViewById(R.id.allRecipes);
        allRecipesButton.setOnClickListener(this);
        spa_searchButton2 = (TextView) view.findViewById(R.id.spa_searchButton2);
        spa_searchButton2.setOnClickListener(this);

        return view;
    }

    public static StartPageFragment getFragment()
    {
        Bundle args = new Bundle();
        StartPageFragment fragment = new StartPageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.spa_searchButton:
                fragment = SearchRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;
            case R.id.allRecipes:
                fragment = AllRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;
            case R.id.spa_searchButton2:
                fragment = SearchRecipesFragment_2.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;

            default:
                break;
        }
    }
}