package com.acer.recipes.Fragments;

import android.os.Bundle;

public class AllRecipesFragment extends AbstractRecipesResultFragment {

    @Override
    public void initializeFragmentFields() {
        outToServer = myConst.GET_ALL_RECIPES;
    }

    public static AllRecipesFragment getFragment()  {
        Bundle args = new Bundle();
        AllRecipesFragment fragment = new AllRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }
}

