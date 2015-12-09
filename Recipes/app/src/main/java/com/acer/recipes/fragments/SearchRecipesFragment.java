package com.acer.recipes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.acer.recipes.Constants;
import com.acer.recipes.DbOpenHelper;
import com.acer.recipes.Product;
import com.acer.recipes.R;
import com.acer.recipes.RecipesResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SearchRecipesFragment extends Fragment implements View.OnClickListener{

    private static final int LAYOUT = R.layout.activity_search_recipes;

    private View view;
    Button sa_searchButton;
    Button sa_helpButton;
    EditText sa_editText;
    DbOpenHelper dbHelper;
    ListView productsListView;

    ArrayList<String> productsArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new Constants().dbHelper;
        sa_editText = (EditText) view.findViewById(R.id.et_product);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton);
        sa_helpButton = (Button) view.findViewById(R.id.sa_helpButton);


        productsListView = (ListView) view.findViewById(R.id.products_listView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productsArrayList);
        productsListView.setAdapter(adapter);

        sa_searchButton.setOnClickListener(this);
        sa_helpButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sa_helpButton: {
                ArrayList<Product> productsArrayList_1 = dbHelper.getProducts("соль");
                for(Product pr : productsArrayList_1)
                {
                    productsArrayList.add(pr.getName());
                    adapter.notifyDataSetChanged();
                }

                //sa_editText.setText(productsArrayList_1.get(0).getName());

                break;
            }
            case R.id.sa_searchButton: {
                Intent intent = new Intent(getActivity(), RecipesResult.class);
                startActivity(intent);
                break;
            }
        }
    }

    public static SearchRecipesFragment getFragment()
    {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
