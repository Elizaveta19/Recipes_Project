package com.acer.recipes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.acer.recipes.R;

public class StartPageFragment extends Fragment implements View.OnClickListener{

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.activity_start_page;
    private View view;

    Button spa_searchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        spa_searchButton = (Button) view.findViewById(R.id.spa_searchButton);
        spa_searchButton.setOnClickListener(this);

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
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;
            default:
                break;
        }
    }
}