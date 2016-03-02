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

        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        String measure = getString(R.string.measure);

        TextView tv_0 = (TextView) view.findViewById(R.id.recipe_fragment_calories);
        tv_0.setText("Calories: "  + Integer.toString(recipe.getCalories()));

        //FAT-----------------------------
        TextView tv_1 = (TextView) view.findViewById(R.id.total_fat);
        String text_1 = tv_1.getText().toString();
        tv_1.setText(text_1 + Integer.toString(recipe.getFat().getTotal()) + measure);

        TextView tv_11 = (TextView) view.findViewById(R.id.fat_dialy_percent);
        String text_11 = tv_11.getText().toString();
        tv_11.setText(Integer.toString(recipe.getFat().getDaily()) + text_11);

        TextView tv_12 = (TextView) view.findViewById(R.id.saturated);
        String text_12 = tv_12.getText().toString();
        tv_12.setText(text_12 + Integer.toString(recipe.getFat().getSaturated()) + measure);

        TextView tv_13 = (TextView) view.findViewById(R.id.trans);
        String text_13 = tv_13.getText().toString();
        tv_13.setText(text_13 + Integer.toString(recipe.getFat().getTrans()) + measure);

        TextView tv_14 = (TextView) view.findViewById(R.id.monounsaturated);
        String text_14 = tv_14.getText().toString();
        tv_14.setText(text_14 + Integer.toString(recipe.getFat().getMonounsaturated()) + measure);

        TextView tv_15 = (TextView) view.findViewById(R.id.polyunsaturated);
        String text_15 = tv_15.getText().toString();
        tv_15.setText(text_15 + Integer.toString(recipe.getFat().getPolyunsaturated()) + measure);
        //FAT---------------------------------------


        //CARBS--------------------------
        TextView tv_2 = (TextView) view.findViewById(R.id.total_carb);
        String text_2 = tv_2.getText().toString();
        tv_2.setText(text_2 + Integer.toString(recipe.getCarbs().getTotal()) + measure);

        TextView tv_21 = (TextView) view.findViewById(R.id.carbs_dialy_percent);
        String text_21 = tv_21.getText().toString();
        tv_21.setText(Integer.toString(recipe.getCarbs().getDaily()) + text_21);

        TextView tv_22 = (TextView) view.findViewById(R.id.carbs_net);
        String text_22 = tv_22.getText().toString();
        tv_22.setText(text_22 + Integer.toString(recipe.getCarbs().getCarbsNet()) + measure);

        TextView tv_23 = (TextView) view.findViewById(R.id.fiber);
        String text_23 = tv_23.getText().toString();
        tv_23.setText(text_23 + Integer.toString(recipe.getCarbs().getFilber()) + measure);

        TextView tv_24 = (TextView) view.findViewById(R.id.sugars);
        String text_24 = tv_24.getText().toString();
        tv_24.setText(text_24 + Integer.toString(recipe.getCarbs().getSugars()) + measure);

        //CARBS--------------------------------------



        //PROTEIN----------------------------------
        TextView tv_3 = (TextView) view.findViewById(R.id.total_protein);
        String text_3 = tv_3.getText().toString();
        tv_3.setText(text_3 + Integer.toString(recipe.getProtein().getTotal()) + measure);

        TextView tv_31 = (TextView) view.findViewById(R.id.protein_dialy_percent);
        String text_31 = tv_31.getText().toString();
        tv_31.setText(Integer.toString(recipe.getProtein().getDaily()) + text_31);

        //PROTEIN---------------------------------

        return view;
    }

}