package com.acer.recipes.Fragments;

import android.os.Bundle;

public class RecipesResultFragment extends AbstractRecipesResultFragment {

    @Override
    public void setFragmentFields() {
        Bundle bundle = getArguments();
        query = bundle.getString("query");
        outToServer = myConst.GET_RECIPES_ADDRESS + query;
        outToServerFromTo = outToServer;
    }

    public static RecipesResultFragment getFragment()  {
        Bundle args = new Bundle();
        RecipesResultFragment fragment = new RecipesResultFragment();
        fragment.setArguments(args);

        return fragment;
    }
}

