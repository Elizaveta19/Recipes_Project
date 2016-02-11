package com.acer.recipes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJson {
    final String LOG_TAG = "myLogs";
    public String getAllProducts(URL fullUrl)
    {
        try {
            StringBuffer result = new StringBuffer();
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) fullUrl.openConnection();
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

    public String getAllRecipes(URL fullUrl)
    {
        try {
            StringBuffer result = new StringBuffer();
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) fullUrl.openConnection();
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

    private JSONArray ConvertEntityToJsonArray(StringBuffer result)
    {
        JSONArray jsonArray = null;
        if(result!= null)
        {
            try {
                Log.d("my5", result.toString());
                String Response = result.toString();
                jsonArray = new JSONArray(Response);
            }
            catch (JSONException e )
            {
                Log.d("my4", e.getMessage());
                e.printStackTrace();
            }
        }
        return  jsonArray;
    }
}
