package com.acer.recipes.RecipeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acer.recipes.R;
import com.acer.recipes.Recipe;

public class NutritionFragment  extends Fragment {

    private static final int LAYOUT = R.layout.nutrition_fragment;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        TextView tv = (TextView) view.findViewById(R.id.recipe_fragment_calories);
        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
                
        tv.setText("Calories: "  + Integer.toString(recipe.getStandartCalories()));

        return view;
    }

}