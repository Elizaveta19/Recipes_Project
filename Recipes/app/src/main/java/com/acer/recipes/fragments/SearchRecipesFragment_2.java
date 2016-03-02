package com.acer.recipes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.acer.recipes.Constants;
import com.acer.recipes.DbOpenHelper;
import com.acer.recipes.R;

public class SearchRecipesFragment_2 extends Fragment implements View.OnClickListener {

    private static final int LAYOUT = R.layout.search_recipes_2;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;

    private View view;
    Button sa_searchButton;
    EditText sa_editText;
    DbOpenHelper dbHelper;
    int ccal = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container,false);

        dbHelper = new Constants().dbHelper;
        sa_editText = (EditText) view.findViewById(R.id.et_product_2);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton_2);

        sa_searchButton.setOnClickListener(this);

        return view;
    }

    public static SearchRecipesFragment_2 getFragment() {
        Bundle args = new Bundle();
        SearchRecipesFragment_2 fragment = new SearchRecipesFragment_2();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sa_searchButton_2: {
                ccal = Integer.parseInt(sa_editText.getText().toString());
                Fragment fragment = new RecipesResultFragment_2();
                Bundle args = new Bundle();
                args.putInt("ccal", ccal);
                fragment.setArguments(args);

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
}
