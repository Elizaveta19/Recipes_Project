package com.acer.recipes.RecipeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acer.recipes.R;
import com.acer.recipes.Recipe;

public class IngredientsFragment extends Fragment{

    private static final int LAYOUT = R.layout.ingredients_fragment;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        ListView listView = (ListView)view.findViewById(R.id.ingredients_list);

        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, recipe.getIngredients());
        listView.setAdapter(adapter);


        return view;
    }

}
