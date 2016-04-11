package com.acer.recipes;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.acer.recipes.Fragments.AllRecipesFragment;
import com.acer.recipes.Fragments.FavoritesFragment;
import com.acer.recipes.Fragments.RecipesResultFragment;
import com.acer.recipes.Fragments.SearchRecipesFragment;
import com.acer.recipes.Fragments.ShoppingListFragment;
import com.acer.recipes.Fragments.StartPageFragment;
import com.acer.recipes.Helpers.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int HOME_ITEM = 0;
    private static final int SEARCH_ITEM = 1;
    private static final int ALL_RECIPES_ITEM = 2;
    private static final int FAVORITES_RECIPES_ITEM = 3;
    private static final int SHOPPING_LIST_ITEM = 4;

    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView leftListView;
    private String[] leftItems;

    private ListView rightListView_1;
    private String[] rightItems_1;

    private ListView rightListView_2;
    private String[] rightItems_2;

    public static final Constants myConst = new Constants();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);
        myConst.dbHelper = new DbHelper(this);

        initDrawer();
        initLeftDrawer();
        if(savedInstanceState == null)
            displayView(HOME_ITEM);

        initDietRightDrawer();
        initHealthRightDrawer();
    }

    private void initDrawer(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);//???
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???
    }

    private void initLeftDrawer(){
        leftItems = getResources().getStringArray(R.array.navigationDrawer);
        leftListView = (ListView) findViewById(R.id.navigation);
        leftListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leftItems));
        leftListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void initDietRightDrawer(){
        rightItems_1 = getResources().getStringArray(R.array.dietLabels);
        rightListView_1 = (ListView) findViewById(R.id.diet_labels_list);
        rightListView_1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, rightItems_1));
        rightListView_1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        rightListView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ctv = (CheckedTextView) view;
                if (ctv.isChecked()) {
                    rightListView_1.clearChoices();
                    rightListView_1.setItemChecked(position, true);
                    Constants.DIET_FILTER = "&diet=";
                    Constants.DIET_FILTER += rightItems_1[position].toLowerCase();
                } else {
                    rightListView_1.clearChoices();
                    rightListView_1.setItemChecked(position, false);
                    Constants.DIET_FILTER = "";
                }
            }
        });
    }

    private void initHealthRightDrawer(){
        rightItems_2 = getResources().getStringArray(R.array.healthLabels);
        rightListView_2 = (ListView) findViewById(R.id.health_labels_list);
        rightListView_2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, rightItems_2));
        rightListView_2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        rightListView_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ctv = (CheckedTextView) view;
                if (ctv.isChecked()) {
                    rightListView_2.clearChoices();
                    rightListView_2.setItemChecked(position, true);
                    Constants.HEALTH_FILTER = "&health=";
                    Constants.HEALTH_FILTER += rightItems_2[position].toLowerCase();
                } else {
                    rightListView_2.clearChoices();
                    rightListView_2.setItemChecked(position, false);
                    Constants.HEALTH_FILTER = "";
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem rightDrawerItem = menu.findItem(R.id.action_right_drawer);
        rightDrawerItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (drawerLayout.isDrawerOpen(findViewById(R.id.health_labels_drawer_layout))){
                    drawerLayout.closeDrawer(findViewById(R.id.health_labels_drawer_layout));
                }
                else{
                    drawerLayout.openDrawer(findViewById(R.id.health_labels_drawer_layout));
                }
                return false;
            }
        });

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.action_search));

        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                drawerLayout.closeDrawer(findViewById(R.id.health_labels_drawer_layout));
                drawerLayout.closeDrawer(findViewById(R.id.navigation));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                drawerLayout.closeDrawer(findViewById(R.id.health_labels_drawer_layout));
                Fragment fragment = RecipesResultFragment.getFragment();
                Bundle args = new Bundle();
                args.putString("query", query.trim());
                fragment.setArguments(args);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        leftListView.setItemChecked(position, true);
        setTitle(leftItems[position]);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case HOME_ITEM:
                fragment = StartPageFragment.getFragment();
                break;
            case SEARCH_ITEM:
                fragment = SearchRecipesFragment.getFragment();
                break;
            case ALL_RECIPES_ITEM:
                fragment = AllRecipesFragment.getFragment();
                break;
            case FAVORITES_RECIPES_ITEM:
                fragment = FavoritesFragment.getFragment();
                break;
            case SHOPPING_LIST_ITEM:
                fragment = ShoppingListFragment.getFragment();
                break;
            default:
                break;
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT_FRAME_ID, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            drawerLayout.closeDrawer(leftListView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    public void setTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

}
