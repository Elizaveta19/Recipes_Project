package com.acer.recipes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.acer.recipes.RecipeFragments.DirectionsFragment;
import com.acer.recipes.RecipeFragments.IngredientsFragment;
import com.acer.recipes.RecipeFragments.NutritionFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public TabsPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new IngredientsFragment();

            case 1:
                return new NutritionFragment();

            case 2:
                return new DirectionsFragment();
        }

        return null;

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return NumbOfTabs;
    }
}