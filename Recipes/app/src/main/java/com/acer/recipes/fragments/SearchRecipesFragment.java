package com.acer.recipes.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
                Fragment fragment = RecipesResultFragment_2.getFragment();
                Bundle args = new Bundle();
                args.putString("query", et_product.getText().toString());
                args.putInt("maxCalories", Integer.parseInt(et_max_calories.getText().toString()));
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

    private void productExistsMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Продукт уже есть в списке")
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void productNotFoundMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Продукта нет в базе данных")
                .setMessage("Необходимо выбрать продукт из выпадающего списка.")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
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
