package com.acer.recipes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.acer.recipes.Constants;
import com.acer.recipes.R;
import com.acer.recipes.Recipe;
import com.acer.recipes.RecipeFragments.SlidingTabLayout;
import com.acer.recipes.RecipeFragments.TabsPagerAdapter;
import com.squareup.picasso.Picasso;


public class RecipeFragment extends FragmentActivity implements View.OnClickListener, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private static final int LAYOUT = R.layout.recipe_fragment;
    private View view;

    ViewPager mViewPager;
    private TabHost mTabHost;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Ingredients", "Nutrition", "Directions"};
    int NumberOfTabs = 3;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppDefault);
        setContentView(LAYOUT);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), Titles, NumberOfTabs));

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });

        tabs.setViewPager(mViewPager);

        final Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        TextView tv = (TextView) findViewById(R.id.recipe_title);
        ImageView recipeHeader = (ImageView) findViewById(R.id.recipe_header);
        //ImageView recipeHeader2 = (ImageView) findViewById(R.id.recipe_header2);
        ImageView backButton = (ImageView) findViewById(R.id.back_to_results);

        tv.setText(recipe.getTitle());
        Picasso.with(this).load(recipe.getImgUrl()).into(recipeHeader);

        backButton.setOnClickListener(this);

        CheckBox checkBox = (CheckBox) findViewById(R.id.favorite_button_recipe_fragment);
        checkBox.setChecked(recipe.isFavorite());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Constants myConst = new Constants();
                try {
                    if (isChecked) {
                        recipe.setFavorite();
                        myConst.dbHelper.addRecipe(recipe);
                    } else {
                        recipe.unsetFavorite();
                        myConst.dbHelper.deleteRecipe(recipe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    for (StackTraceElement ste : e.getStackTrace())
                        Log.v("Ошибка============", ste.toString());
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ImageView recipeHeader = (ImageView) findViewById(R.id.recipe_header);
        ImageView recipeHeader2 = (ImageView) findViewById(R.id.recipe_header2);

        int width = findViewById(R.id.recipe_recipe_header).getMeasuredWidth();
        int height = findViewById(R.id.recipe_title_layout).getMeasuredHeight();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        recipeHeader.setLayoutParams(params);
        recipeHeader2.setLayoutParams(params);

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
    public void onClick(View v) {
        super.onBackPressed();
    }
}
