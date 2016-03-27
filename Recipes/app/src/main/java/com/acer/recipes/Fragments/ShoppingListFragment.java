package com.acer.recipes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acer.recipes.BusStation;
import com.acer.recipes.Constants;
import com.acer.recipes.RecipeNutrition.Ingredient;
import com.acer.recipes.R;
import com.acer.recipes.ShoppingListAdapter;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment{
    private static final int LAYOUT = R.layout.shopping_list;
    private static final int C_MENU_DELETE = 101;
    private View view;
    ListView  listView;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ArrayAdapter<Ingredient> adapter;
    static Ingredient itemAtPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container,false);
        listView = (ListView) view.findViewById(R.id.shopping_list);
        ingredients = Constants.dbHelper.getShoppingList();
        adapter = new ShoppingListAdapter(getActivity(), ingredients);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        itemAtPosition = (Ingredient) listView.getItemAtPosition(acmi.position);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, C_MENU_DELETE, menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case C_MENU_DELETE:
                Constants.dbHelper.deleteIngredientFromShoppingList(itemAtPosition.getTitle());
                ingredients.remove(itemAtPosition);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    /*@Subscribe
    public void onEvent(Message message){
        Constants.SHOPPING_LIST.add(message.getMessage());
    }*/

    public static ShoppingListFragment getFragment()  {
        Bundle args = new Bundle();
        ShoppingListFragment fragment = new ShoppingListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume(){
        super.onResume();
        BusStation.getBus().register(this);
    }
}
