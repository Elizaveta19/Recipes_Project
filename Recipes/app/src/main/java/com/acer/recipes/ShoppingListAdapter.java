package com.acer.recipes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.acer.recipes.RecipeNutrition.Ingredient;

import java.util.ArrayList;

public class ShoppingListAdapter extends ArrayAdapter<Ingredient> {

    public ShoppingListAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, R.layout.shopping_list_item, ingredients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ingredient ingredient = getItem(position);

        View v = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.shopping_list_item, null);
        final TextView textView = (TextView) v.findViewById(R.id.ingredient_text);
        textView.setText(ingredient.getTitle());

        CheckBox checkBox = (CheckBox) v.findViewById(R.id.check);
        checkBox.setChecked(ingredient.getIsBought());
        if(ingredient.getIsBought()){
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Constants.dbHelper.setBoughtIngredient(ingredient);
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    Constants.dbHelper.unsetBoughtIngredient(ingredient);
                    textView.setPaintFlags(textView.getPaintFlags()  & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        return v;
    }
}
