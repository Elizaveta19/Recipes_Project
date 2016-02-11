package com.acer.recipes.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.acer.recipes.Constants;
import com.acer.recipes.R;
import com.acer.recipes.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RecipesResultFragment_2 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    private static final int CONTENT_FRAME_ID = R.id.content_frame;
    private static final int LAYOUT = R.layout.activity_recipes_result;
    private static final int ITEM_LAYOUT = R.layout.recipe_list_item;
    private View view;

    Socket client = null;
    String comment = new String();
    String inputFromServer = new String();
    String outToServer = new String();

    public static final Constants myConst = new Constants();
    MyTask myTask;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    ListView listView;

    int ccal = 0;
    private ArrayList<HashMap<String, Object>> myRecipes;
    private static final String RECIPE_TITLE = "title";    // Главное название, большими буквами
    private static final String TIME = "time";  // Наименование, то что ниже главного
    private static final String CCAL = "ccal";  // Наименование, то что ниже главного

    Button arr_backButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        Bundle bundle = getArguments();
        ccal = bundle.getInt("ccal");

        listView = (ListView) view.findViewById(R.id.arr_recipes_listView);
        listView.setOnItemClickListener(this);

        arr_backButton = (Button) view.findViewById(R.id.arr_backButton);
        arr_backButton.setOnClickListener(this);

        myTask = new MyTask();
        myTask.execute();
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Recipe[] recipeArray = new Recipe[recipeArrayList.size()];
        Recipe recipe = recipeArrayList.get(position);
        Fragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", recipe);
        fragment.setArguments(args);

        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arr_backButton: {
                Fragment fragment = SearchRecipesFragment.getFragment();
                if(fragment != null)
                {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(CONTENT_FRAME_ID, fragment).commit();
                }
                break;
            }
            default:
                break;
        }
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {
        String title;
        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL fullUrl = new URL(myConst.GET_RECIPES_BY_CCAL_ADDRESS);
                StringBuffer result = new StringBuffer();
                HttpURLConnection urlConnection = (HttpURLConnection) fullUrl.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                try {
                    InputStream sin = urlConnection.getInputStream();
                    OutputStream sout = urlConnection.getOutputStream();

                    JSONObject jsonOut = new JSONObject();
                    jsonOut.put("ccal", ccal);
                    outToServer = jsonOut.toString();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sout, "UTF-8"));
                    writer.write(outToServer);
                    writer.flush();
                    writer.close();
                    sout.close();

                    BufferedReader instr = new BufferedReader(new InputStreamReader(sin));
                    String str = null;
                    while ((str = instr.readLine()) != null) {
                        inputFromServer += str;
                    }
                }
                catch (IOException e) {
                    comment = ("Не удалось подключится к " + myConst.SERVER_ADDRESS);
                    for (StackTraceElement ste : e.getStackTrace())
                        Log.v("Ошибка============", ste.toString());
                }
                finally {
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                comment = ("Не удалось подключится к " + myConst.SERVER_ADDRESS);
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                JSONObject reader = new JSONObject(inputFromServer);
                JSONArray recipe = reader.getJSONArray("recipe");

                recipeArrayList.clear();
                for(int i = 0; i < recipe.length(); i++)
                {
                    JSONObject jsonObject = recipe.getJSONObject(i);

                    int id = Integer.parseInt(jsonObject.getString("id_recipe").toString());
                    String  title = jsonObject.getString("title").toString();
                    String  ingredients = jsonObject.getString("ingredients").toString();
                    String text = jsonObject.getString("text").toString();
                    int ccal = Integer.parseInt(jsonObject.getString("ccal").toString());
                    String time = jsonObject.getString("time").toString();

                    recipeArrayList.add(new Recipe(id, title, ingredients, text, ccal, time));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                for(StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            myRecipes = new ArrayList<HashMap<String, Object>>();      //создаем массив списков
            HashMap<String, Object> hm;                             //список объектов

            //С помощью ключевого HashMap добавляем название (то что большими буквами), и описание (маленькими)
            for (Recipe recipe : recipeArrayList)
            {
                hm = new HashMap<String, Object>();
                hm.put(RECIPE_TITLE, recipe.getTitle());
                hm.put(TIME,  "Время приготовления: " + recipe.getTime());
                hm.put(CCAL, "Калорийность на 100 гр: " + String.valueOf(recipe.getCcal()) + " ккал");
                myRecipes.add(hm);//Добавляем на форму для отображения, без этой функции мы не видим сам вью
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                    myRecipes,
                    ITEM_LAYOUT,
                    new String[]{ // массив названий
                            RECIPE_TITLE,         //верхний текст
                            TIME,        //нижний теккт
                            CCAL,        //нижний теккт
                    },
                    new int[]{    //массив форм
                            R.id.text1,      //наш id TextBox'a в list.xml
                            R.id.text2,      //наш id TextBox'a в list.xml
                            R.id.text3});    //ссылка на объект отображающий текст
            listView.setAdapter(adapter); //говорим программе что бы отображала все объекты
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  //Даем возможность выбора если есть желание сделать переход на другие формы

            super.onPostExecute(result);
        }
    }
}

