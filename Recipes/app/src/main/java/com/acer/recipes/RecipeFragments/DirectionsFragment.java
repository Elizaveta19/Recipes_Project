package com.acer.recipes.RecipeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.acer.recipes.R;
import com.acer.recipes.Recipe;

public class DirectionsFragment  extends Fragment {

    private static final int LAYOUT = R.layout.directions_fragment;
    private View view;

    private WebView mWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Recipe recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        mWebView = (WebView) view.findViewById(R.id.webView);     // включаем поддержку JavaScript
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);        // указываем страницу загрузки
        mWebView.loadUrl(recipe.getUrl());

        return view;
    }
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }


}