package com.acer.recipes.Helpers;

import android.util.Log;

import com.acer.recipes.Recipe;
import com.acer.recipes.RecipeNutrition.Carbs;
import com.acer.recipes.RecipeNutrition.Fat;
import com.acer.recipes.RecipeNutrition.Protein;

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

import static com.acer.recipes.Recipe.*;

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
                JSONArray ingredientsJSON = jsonRecipeObject.getJSONArray("ingredientLines");
                ArrayList<String> ingredients = new ArrayList<>();
                for(int j = 0; j < ingredientsJSON.length(); j++)
                {
                    ingredients.add(ingredientsJSON.getString(j));
                }

                String uri = jsonRecipeObject.getString("uri").toString();
                String imgUrl = jsonRecipeObject.getString("image").toString();
                String sourceUrl = jsonRecipeObject.getString("url").toString();
                int ccal = (int) Double.parseDouble(jsonRecipeObject.getString("calories").toString());
                int totalWeight = (int) Double.parseDouble(jsonRecipeObject.getString("totalWeight").toString());

                JSONArray digestJSON = jsonRecipeObject.getJSONArray("digest");
                JSONObject fatItemJSON = digestJSON.getJSONObject(0);
                int totalFat = fatItemJSON.getInt("total");
                int dailyFat = fatItemJSON.getInt("daily");

                JSONArray fatSubJSON = fatItemJSON.getJSONArray("sub");
                int saturated = fatSubJSON.getJSONObject(0).getInt("total");
                int trans = fatSubJSON.getJSONObject(1).getInt("total");
                int mono = fatSubJSON.getJSONObject(2).getInt("total");
                int poly = fatSubJSON.getJSONObject(3).getInt("total");
                Fat fat = new Fat(totalWeight, totalFat, dailyFat, saturated, trans, mono, poly);

                JSONObject carbsItemJSON = digestJSON.getJSONObject(1);
                int totalCarbs = carbsItemJSON.getInt("total");
                int dailyCarbs = carbsItemJSON.getInt("daily");
                JSONArray carbsSubJSON = carbsItemJSON.getJSONArray("sub");
                int carbsNet = carbsSubJSON.getJSONObject(0).getInt("total");
                int fiber = carbsSubJSON.getJSONObject(1).getInt("total");
                int sugar = carbsSubJSON.getJSONObject(2).getInt("total");
                Carbs carbs = new Carbs(totalWeight, totalCarbs, dailyCarbs, carbsNet, fiber, sugar);

                JSONObject proteinItemJSON = digestJSON.getJSONObject(2);
                int totalProtein = proteinItemJSON.getInt("total");
                int dailyProtein = proteinItemJSON.getInt("daily");
                Protein protein = new Protein(totalWeight, totalProtein, dailyProtein);

                recipeArrayList.add(new Recipe(uri, title, ingredients, sourceUrl, ccal, totalWeight, imgUrl, fat, carbs, protein, false));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
            for (StackTraceElement ste : e.getStackTrace())
                Log.v("Ошибка============", ste.toString());
        }
    }
}
