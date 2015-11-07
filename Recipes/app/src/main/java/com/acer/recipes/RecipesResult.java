package com.acer.recipes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RecipesResult extends Activity{
    private static final int LAYOUT = R.layout.activity_recipes_result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_result);

        MyTask mt = new MyTask();
        mt.execute();
    }



    class MyTask extends AsyncTask<Void, Void, Void>
    {

        String title;
        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://harrix.org").get();
            }
            catch (IOException e) {
                Log.v("MyConnect", "Connect error :c");
                e.printStackTrace();
            }

            if(doc != null)
                title = doc.title();
            else
                title = "Ошибка";

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            TextView textView = (TextView) findViewById(R.id.arr_name);
            super.onPostExecute(result);
            textView.setText(title);
        }
    }
}
