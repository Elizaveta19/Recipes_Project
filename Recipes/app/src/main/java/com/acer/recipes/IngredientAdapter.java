package com.acer.recipes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;


public class IngredientAdapter extends ArrayAdapter<String> {
    Recipe recipe;

    public IngredientAdapter(Context context, Recipe _recipe) {
        super(context, R.layout.ingredient_item, _recipe.getIngredients());
        recipe = _recipe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String ingredient = getItem(position);

        View v = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.ingredient_item, null);
        ((TextView) v.findViewById(R.id.ingredient_text)).setText(ingredient);

        CheckBox checkBox = (CheckBox) v.findViewById(R.id.check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Constants.dbHelper.addIngredientToShoppingList(recipe, ingredient);
                //Constants.SHOPPING_LIST.add(ingredient);
                //BusStation.getBus().post(new Message(ingredient));
                //Log.v("Отправлено сообщение==", ingredient);
            }
        });

        return v;
    }
}
