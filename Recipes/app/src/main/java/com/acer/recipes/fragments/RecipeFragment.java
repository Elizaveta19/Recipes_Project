package com.acer.recipes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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


public class RecipeFragment extends AppCompatActivity implements View.OnClickListener{//extends Fragment implements View.OnClickListener{

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

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        //Bundle bundle = getArguments();
        //recipe = savedInstanceState.getParcelable("recipe");
        //С помощью ключевого HashMap добавляем название (то что большими буквами), и описание (маленькими)

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

       // String recipeUrl =(String) b.get("recipeUrl");

        String recipeUrl = getIntent().getStringExtra("recipeUrl");

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        rf_backButton = (Button) findViewById(R.id.rf_backButton);
        //rf_backButton.setOnClickListener(this);

        //textView1.setText(recipe.getTitle());
        //textView2.setText("Время приготовления: " + recipe.getTime());
        textView3.setText("Ссылка на сайт-источник: " + recipeUrl);//recipe.getRecipeUrl());
        //textView4.setText("Ингредиенты: \n" + recipe.getIngredients());
        //textView5.setText("Рецепт приготовления: \n" + recipe.getText());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rf_backButton: {

                /*Fragment fragment = SearchRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }*/
                break;
            }
            default:
                break;
            }
    }
}
