package com.acer.recipes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class JsonManager {
    final String LOG_TAG = "myLogs";
    public String getAllProducts(URL fullUrl) {
        try {
            StringBuffer result = new StringBuffer();
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) fullUrl.openConnection();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String st = "";
                    while ((st = br.readLine()) != null) {
                        result.append(st);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Log.d("my1", e.getMessage());
                }
                finally {
                    urlConnection.disconnect();
                }
            }catch (MalformedURLException e)
            {
                Log.d("my2", e.getMessage());
                e.printStackTrace();
            }
            //JSONArray jArray =  ConvertEntityToJsonArray(result);
            //return  jArray;
            return result.toString();
        }
        catch (IOException e)
        {
            Log.d("my3", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String getAllRecipes(URL fullUrl) {
        try {
            StringBuffer result = new StringBuffer();
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) fullUrl.openConnection();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String st = "";
                    while ( (st= br.readLine()) != null) {
                        result.append(st);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Log.d("my1", e.getMessage());
                }
                finally {
                    urlConnection.disconnect();
                }
            }catch (MalformedURLException e)
            {
                Log.d("my2", e.getMessage());
                e.printStackTrace();
            }
            //JSONArray jArray =  ConvertEntityToJsonArray(result);
            //return  jArray;
            return result.toString();
        }
        catch (IOException e)
        {
            Log.d("my3", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void putRecipes(String inputFromServer, ArrayList<Recipe> recipeArrayList)
    {
        try
        {
            JSONObject reader = new JSONObject(inputFromServer);
            JSONArray recipe = reader.getJSONArray("hits");

            for (int i = 0; i < recipe.length(); i++) {
                JSONObject jsonObject = recipe.getJSONObject(i);
                JSONObject jsonRecipeObject = jsonObject.getJSONObject("recipe");

                String title = jsonRecipeObject.getString("label").toString();
                String imgUrl = jsonRecipeObject.getString("image").toString();
                String recipeUrl = jsonRecipeObject.getString("url").toString();
                int ccal = (int) Double.parseDouble(jsonRecipeObject.getString("calories").toString());
                int totalWeight = (int) Double.parseDouble(jsonRecipeObject.getString("totalWeight").toString());

                int id = 1;

                recipeArrayList.add(new Recipe(id, title, "", recipeUrl, ccal, totalWeight, imgUrl));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
            for (StackTraceElement ste : e.getStackTrace())
                Log.v("Ошибка============", ste.toString());
        }
    }
}
