package com.acer.recipes;

import android.app.Activity;
import android.os.Bundle;

public class AllProductsActivity extends Activity {
    private static final int LAYOUT = R.layout.activity_all_products;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_result);
    }
}