package com.acer.recipes;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private static final int LAYOUT = R.layout.activity_main;
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

        //listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navigationItems));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), navigationItems[position] + " was selected", Toast.LENGTH_LONG).show();
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

    private void initNavigationView() {


        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, "Open", "Close");
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public  boolean onNavigationItemSelected(MenuItem menuItem)
            {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.homeItem:
                        //show
                }
                return false;
            }
        });*/
    }

    private void initTabs()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
       /* TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
