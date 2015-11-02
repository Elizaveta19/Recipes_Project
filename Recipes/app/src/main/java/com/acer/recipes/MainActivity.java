package com.acer.recipes;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acer.recipes.fragments.SearchRecipesFragment;
import com.acer.recipes.fragments.StartPageFragment;

public class MainActivity extends AppCompatActivity {

    private static final int HOME_ITEM = 0;
    private static final int SEARCH_ITEM = 1;

    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] navigationItems;
    private ActionBarDrawerToggle drawerLitener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);


        navigationItems = getResources().getStringArray(R.array.navigationDrawer);
        listView = (ListView) findViewById(R.id.navigation);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navigationItems));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case HOME_ITEM:
                    {
                        StartPageFragment fragment = StartPageFragment.getFragment();
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                        drawerLayout.closeDrawer(listView);
                        break;
                    }

                    case SEARCH_ITEM:
                    {
                        SearchRecipesFragment fragment = SearchRecipesFragment.getFragment();
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                        drawerLayout.closeDrawer(listView);
                        break;
                    }
                }

                selectItem(position);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLitener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerLitener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
