package com.acer.recipes.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.acer.recipes.Constants;
import com.acer.recipes.DbOpenHelper;
import com.acer.recipes.Product;
import com.acer.recipes.R;

import java.util.ArrayList;

public class SearchRecipesFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final int LAYOUT = R.layout.search_recipes;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;

    public static final int C_MENU_DELETE = 201;

    private View view;
    DbOpenHelper dbHelper;

    AutoCompleteTextView myAutoComplete;
    ArrayList<String> autoCompleteArrayList_string = new ArrayList<>();
    ArrayList<Product> autoCompleteArrayList_product;
    ArrayAdapter<String> autoCompleteAdapter_string;

    Button sa_OkButton;
    Button sa_searchButton;
    Button sa_clearButton;

    ListView productsListView;
    ArrayList<String> productsArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    static String itemAtPosition = new String();

    ArrayList<Integer> keysList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container,false);

        dbHelper = new Constants().dbHelper;
        sa_clearButton = (Button) view.findViewById(R.id.sa_clearButton);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton);

        myAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.autoText);

        prepareMyList();
        myAutoComplete.addTextChangedListener(this);

        autoCompleteAdapter_string = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, autoCompleteArrayList_string);
        myAutoComplete.setAdapter(autoCompleteAdapter_string);

        sa_OkButton = (Button) view.findViewById(R.id.sa_OkButton);
        sa_OkButton.setOnClickListener(this);

        //выбранный писок продуктов
        productsListView = (ListView) view.findViewById(R.id.products_listView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, productsArrayList);
        productsListView.setAdapter(adapter);
        registerForContextMenu(productsListView);

        sa_searchButton.setOnClickListener(this);
        sa_clearButton.setOnClickListener(this);

        return view;
    }

    private void prepareMyList() {
        // put all products to the AutoComplete
        autoCompleteArrayList_string.clear();
        autoCompleteArrayList_product = dbHelper.getProducts("");
        for (Product pr : autoCompleteArrayList_product) {
            autoCompleteArrayList_string.add(pr.getName());
       }
    }

    public static SearchRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

             case R.id.sa_OkButton: {
                 String newAdd = myAutoComplete.getText().toString().trim();
                 Product pr = dbHelper.getProductByName(newAdd);

                 if (pr != null) {
                     int s = pr.getId();
                     if (keysList.contains(s)) {
                         productExistsMessage();
                     }
                     else {
                         keysList.add(s);
                         productsArrayList.add(pr.getName());
                         adapter.notifyDataSetChanged();
                     }
                 }

                 else {
                     productNotFoundMessage();
                 }
                 myAutoComplete.setText("");
                break;
            }
            case R.id.sa_clearButton: {
                productsArrayList.clear();
                adapter.notifyDataSetChanged();
                break;
            }
            case R.id.sa_searchButton: {
                Fragment fragment = new RecipesResultFragment();
                Bundle args = new Bundle();
                args.putIntegerArrayList("keysList", keysList);
                fragment.setArguments(args);

                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;

            }
            default:
                break;
        }
    }

    private void productExistsMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Продукт уже есть в списке")
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void productNotFoundMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Продукта нет в базе данных")
                .setMessage("Необходимо выбрать продукт из выпадающего списка.")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        itemAtPosition = (String) productsListView.getItemAtPosition(acmi.position);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, C_MENU_DELETE, menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case C_MENU_DELETE:
                productsArrayList.remove(itemAtPosition);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
