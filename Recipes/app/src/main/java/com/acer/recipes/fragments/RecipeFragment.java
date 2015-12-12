package com.acer.recipes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.acer.recipes.R;
import com.acer.recipes.Recipe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class RecipeFragment extends Fragment implements View.OnClickListener{

    private static final int LAYOUT = R.layout.recipe_fragment;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private View view;

    Recipe recipe;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    Button rf_backButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Bundle bundle = getArguments();
        recipe = bundle.getParcelable("recipe");
        //С помощью ключевого HashMap добавляем название (то что большими буквами), и описание (маленькими)

        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);

        rf_backButton = (Button) view.findViewById(R.id.rf_backButton);
        rf_backButton.setOnClickListener(this);

        textView1.setText(recipe.getTitle());
        textView2.setText("Время приготовления: " + recipe.getTime());
        textView3.setText("Калорийность на 100 гр: " + String.valueOf(recipe.getCcal()) + " ккал");
        textView4.setText("Ингредиенты: \n" + recipe.getIngredients());
        textView5.setText("Рецепт приготовления: \n" + recipe.getText());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rf_backButton: {

                Fragment fragment = SearchRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;
            }
            default:
                break;
            }
    }
}
