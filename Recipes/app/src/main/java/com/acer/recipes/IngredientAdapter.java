package com.acer.recipes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class IngredientAdapter extends ArrayAdapter<String> {
    private Recipe recipe;

    public IngredientAdapter(Context context, Recipe _recipe) {
        super(context, R.layout.ingredient_item, _recipe.getIngredients());
        recipe = _recipe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String ingredient = getItem(position);

        View v = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.ingredient_item, null);

        CheckBox checkBox = (CheckBox) v.findViewById(R.id.ingredient_fragment_check);
        checkBox.setText(ingredient);
        checkBox.setChecked(Constants.dbHelper.isInShoppingList(recipe, ingredient));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constants.dbHelper.addIngredientToShoppingList(recipe, ingredient);
                } else {
                    Constants.dbHelper.deleteIngredientFromShoppingList(ingredient);
                }

            }
        });

        return v;
    }
}
