package com.acer.recipes.RecipeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.acer.recipes.IngredientAdapter;
import com.acer.recipes.R;
import com.acer.recipes.Recipe;

public class IngredientsFragment extends Fragment{

    private static final int LAYOUT = R.layout.ingredients_fragment;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        final ListView listView = (ListView)view.findViewById(R.id.ingredients_list);
        CheckBox selectAll = (CheckBox) view.findViewById(R.id.select_all_checkbox);

        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        ArrayAdapter adapter = new IngredientAdapter(getActivity(), recipe);
        listView.setAdapter(adapter);

        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (listView.getCount() > 0) {
                        for (int i = 0; i < listView.getCount(); i++) {
                            View view = listView.getChildAt(i);
                            CheckBox check = (CheckBox) view.findViewById(R.id.check);
                            check.setChecked(true);
                        }
                    }
                } else {
                    if (listView.getCount() > 0) {
                        for (int i = 0; i < listView.getCount(); i++) {
                            View view = listView.getChildAt(i);
                            CheckBox check = (CheckBox) view.findViewById(R.id.check);
                            check.setChecked(false);
                        }
                    }
                }
            }
        });

        return view;
    }
}
