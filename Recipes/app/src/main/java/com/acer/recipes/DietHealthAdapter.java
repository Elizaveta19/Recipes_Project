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

public class DietHealthAdapter  extends ArrayAdapter<String> {

    Context context;
    public DietHealthAdapter(Context context, ArrayList<String> labels) {
        super(context, R.layout.health_labels_item, labels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String label = getItem(position);

        View v = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.health_labels_item, null);

       /* CheckBox checkBox = (CheckBox) v.findViewById(R.id.diet_health_label);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Constants.DIET_FILTER += label;
                }
                else{
                    //Constants.(label);
                }
            }
        });*/

        return v;
    }
}
