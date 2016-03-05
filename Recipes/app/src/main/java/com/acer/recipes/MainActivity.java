package com.acer.recipes;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acer.recipes.Fragments.AllRecipesFragment;
import com.acer.recipes.Fragments.RecipesResultFragment;
import com.acer.recipes.Fragments.SearchRecipesFragment;
import com.acer.recipes.Fragments.SearchRecipesFragment_2;
import com.acer.recipes.Fragments.StartPageFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int HOME_ITEM = 0;
    private static final int SEARCH_ITEM = 1;
    private static final int SEARCH_ITEM_2 = 2;
    private static final int ALL_RECIPES_ITEM = 3;

    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] navigationItems;
    private ActionBarDrawerToggle drawerLitener;

    private String comment = new String();
    private String inputFromServer = new String();
    private String outToServer = new String();
    public static final Constants myConst = new Constants();
    private MyTask myTask;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);
        myConst.dbHelper = new DbOpenHelper(this);

        navigationItems = getResources().getStringArray(R.array.navigationDrawer);
        listView = (ListView) findViewById(R.id.navigation);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navigationItems));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLitener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(drawerLitener);
        getSupportActionBar().setHomeButtonEnabled(true);//???
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???

        if(savedInstanceState == null)
            displayView(HOME_ITEM);

        SSLCertificateHandler.nuke();

        myTask = new MyTask();
        myTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = RecipesResultFragment.getFragment();
                Bundle args = new Bundle();
                args.putString("query", query.trim());
                fragment.setArguments(args);
                if(fragment != null)
                {
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
            case SEARCH_ITEM_2:
                fragment = SearchRecipesFragment_2.getFragment();
                break;
            case ALL_RECIPES_ITEM:
                fragment = AllRecipesFragment.getFragment();
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
            drawerLayout.closeDrawer(listView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerLitener.onOptionsItemSelected(item))
            return true;

        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerLitener.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerLitener.syncState();
    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);
        setTitle(navigationItems[position]);
    }

    public void setTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
       // String title;
       // JSONArray jsonArray;

        @Override
        protected Void doInBackground(Void... params) {
            JsonManager jsonManager = new JsonManager();
            try {
                URL fullUrl = new URL(myConst.GET_PRODUCTS_ADDRESS);
                inputFromServer = jsonManager.getAllProducts(fullUrl);
                putProducts();
            }catch (Exception e) {
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        protected void putProducts() {
            try {
                JSONObject reader = new JSONObject(inputFromServer);
                JSONArray productsJSON = reader.getJSONArray("products");
                myConst.dbHelper.addProducts(productsJSON);

            } catch (JSONException e) {
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }
        }
    }
}
