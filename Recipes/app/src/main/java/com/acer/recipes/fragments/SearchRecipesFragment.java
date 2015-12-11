package com.acer.recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.acer.recipes.Constants;
import com.acer.recipes.DbOpenHelper;
import com.acer.recipes.Product;
import com.acer.recipes.R;

import java.util.ArrayList;

public class SearchRecipesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int LAYOUT = R.layout.activity_search_recipes;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;

    public static final int C_MENU_DELETE = 201;

    private View view;
    Button sa_searchButton;
    Button sa_helpButton;
    Button sa_clearButton;
    Button sa_cancelButton;
    EditText sa_editText;
    DbOpenHelper dbHelper;

    ListView productsHelperListView;
    ArrayList<String> productsHelperArrayList = new ArrayList<>();
    ArrayAdapter<String> helperAdapter;

    ListView productsListView;
    ArrayList<String> productsArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    static String itemAtPosition = new String();

    ArrayList<Integer> keysList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new Constants().dbHelper;
        sa_editText = (EditText) view.findViewById(R.id.et_product);
        sa_cancelButton = (Button) view.findViewById(R.id.sa_cancelButton);
        sa_helpButton = (Button) view.findViewById(R.id.sa_helpButton);
        sa_clearButton = (Button) view.findViewById(R.id.sa_clearButton);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton);

        //для вспомогательного списка для выбора продуктов
        productsHelperListView = (ListView) view.findViewById(R.id.products_helper_listView);
        helperAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, productsHelperArrayList);
        productsHelperListView.setAdapter(helperAdapter);
        productsHelperListView.setOnItemClickListener(this);

        //выбранный писок продуктов
        productsListView = (ListView) view.findViewById(R.id.products_listView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, productsArrayList);
        productsListView.setAdapter(adapter);
        registerForContextMenu(productsListView);

        sa_helpButton.setOnClickListener(this);
        sa_searchButton.setOnClickListener(this);
        sa_clearButton.setOnClickListener(this);
        sa_cancelButton.setOnClickListener(this);

        return view;
    }

    /*@Override
    public void onDetach() {

        Bundle args = new Bundle();
        args.putIntegerArrayList("keysList", keysList);
        super.onDetach();
    }*/

    public static SearchRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sa_cancelButton: {
                productsHelperArrayList.clear();
                helperAdapter.notifyDataSetChanged();
                sa_editText.setText("");
                sa_cancelButton.setVisibility(View.GONE);
                sa_clearButton.setVisibility(View.VISIBLE);
                sa_searchButton.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            }
            case R.id.sa_helpButton: {
                sa_cancelButton.setVisibility(View.VISIBLE);
                sa_clearButton.setVisibility(View.GONE);
                sa_searchButton.setVisibility(View.GONE);
                productsHelperArrayList.clear();
                ArrayList<Product> productsArrayList_1 = dbHelper.getProducts(sa_editText.getText().toString().trim());
                for(Product pr : productsArrayList_1)
                {
                    productsHelperArrayList.add(pr.getName());
                    helperAdapter.notifyDataSetChanged();
                }

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        sa_cancelButton.setVisibility(View.GONE);
        sa_clearButton.setVisibility(View.VISIBLE);
        sa_searchButton.setVisibility(View.VISIBLE);
        sa_editText.setText("");
        String temp = productsHelperArrayList.get(position);
        Product pr = dbHelper.getProductByName(temp);
        int s = pr.getId();
        keysList.add(s);

        productsArrayList.add(pr.getName());
        adapter.notifyDataSetChanged();

        productsHelperArrayList.clear();
        helperAdapter.notifyDataSetChanged();
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
}
