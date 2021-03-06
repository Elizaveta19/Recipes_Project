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
    CheckBox selectAll;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        listView = (ListView)view.findViewById(R.id.ingredients_list);
        selectAll = (CheckBox) view.findViewById(R.id.select_all_checkbox);

        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        ArrayAdapter adapter = new IngredientAdapter(getActivity(), recipe);
        listView.setAdapter(adapter);

        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setAllChecked(listView);
                } else {
                    setAllUnchecked(listView);
                }
            }
        });

        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if(isAllChecked(listView)){
            selectAll.setChecked(true);
        }
        else {
            selectAll.setChecked(false);
        }
    }*/

    private boolean isAllChecked(ListView listView){
        boolean result = false;
        if (listView.getCount() > 0) {
            for (int i = 0; i < listView.getCount(); i++) {
                View view = listView.getChildAt(i);
                CheckBox check = (CheckBox) view.findViewById(R.id.ingredient_fragment_check);
                result = check.isChecked();
            }
        }
        return result;
    }

    private void setAllChecked(ListView listView){
        if (listView.getCount() > 0) {
            for (int i = 0; i < listView.getCount(); i++) {
                View view = listView.getChildAt(i);
                CheckBox check = (CheckBox) view.findViewById(R.id.ingredient_fragment_check);
                check.setChecked(true);
            }
        }
    }

    private void setAllUnchecked(ListView listView){
        if (listView.getCount() > 0) {
            for (int i = 0; i < listView.getCount(); i++) {
                View view = listView.getChildAt(i);
                CheckBox check = (CheckBox) view.findViewById(R.id.ingredient_fragment_check);
                check.setChecked(false);
            }
        }
    }

}
