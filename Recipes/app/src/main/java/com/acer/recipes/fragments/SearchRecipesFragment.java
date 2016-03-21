package com.acer.recipes.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acer.recipes.R;

public class SearchRecipesFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final int LAYOUT = R.layout.search_recipes;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;

    private View view;
    Button sa_searchButton;
    EditText et_product;
    EditText et_max_calories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container,false);

        et_product = (EditText) view.findViewById(R.id.et_product);
        et_max_calories = (EditText) view.findViewById(R.id.et_max_calories);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton);
        sa_searchButton.setOnClickListener(this);

        return view;
    }

    public static SearchRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sa_searchButton: {
                Fragment fragment = RecipesWithCaloriesResultFragment.getFragment();
                Bundle args = new Bundle();
                if(!et_product.getText().toString().trim().equals("")) {
                    args.putString("query", et_product.getText().toString().trim());
                }
                else {
                    noProductMessage();
                }
                if(!et_max_calories.getText().toString().trim().equals("")) {
                    args.putInt("maxCalories", Integer.parseInt(et_max_calories.getText().toString()));
                }
                else {
                    noCCalMessage();
                }
                fragment.setArguments(args);

                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sa_searchButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                break;

            }
            default:
                break;
        }
    }

    private void noProductMessage(){
        String noProductMessage = "You have not entered any product.";
        Toast toast = Toast.makeText(getContext(), noProductMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void noCCalMessage(){
        String noCCalMessage = "You have not entered max calories.";
        Toast toast = Toast.makeText(getContext(), noCCalMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
