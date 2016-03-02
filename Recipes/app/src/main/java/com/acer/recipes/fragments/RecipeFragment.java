package com.acer.recipes.fragments;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.acer.recipes.R;
import com.acer.recipes.Recipe;
import com.acer.recipes.RecipeFragments.SlidingTabLayout;
import com.acer.recipes.TabsPagerAdapter;


public class RecipeFragment extends FragmentActivity implements ActionBar.TabListener, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private static final int LAYOUT = R.layout.recipe_fragment;
    private View view;

    Recipe recipe;
    ViewPager mViewPager;
    private TabHost mTabHost;
    ActionBar actionBar;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Ingredients", "Nutrition", "Directions"};
    int Numboftabs = 3;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs));

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });

        tabs.setViewPager(mViewPager);


        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        TextView tv = (TextView) findViewById(R.id.recipe_title);
        tv.setText(recipe.getTitle());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int pos = this.mViewPager.getCurrentItem();
        this.mTabHost.setCurrentTab(pos);
    }
    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
