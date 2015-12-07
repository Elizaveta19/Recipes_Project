package com.acer.recipes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.acer.recipes.Constants;
import com.acer.recipes.DbOpenHelper;
import com.acer.recipes.Product;
import com.acer.recipes.R;
import com.acer.recipes.RecipesResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SearchRecipesFragment extends Fragment implements View.OnClickListener{

    private static final int LAYOUT = R.layout.activity_search_recipes;

    private View view;
    Button sa_searchButton;
    Socket client = null;
    String comment = new String();
    String inputFromServer = new String();
    String outToServer = new String();
    MyTask myTask;
    EditText sa_editText;
    public static final Constants myConst = new Constants();
    DbOpenHelper dbHelper;
    ListView productsList;
    ArrayList<Product> products;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        dbHelper = new DbOpenHelper(getContext());
        sa_editText = (EditText) view.findViewById(R.id.et_product);
        sa_searchButton = (Button) view.findViewById(R.id.sa_searchButton);
        sa_searchButton.setOnClickListener(this);

        myTask = new MyTask();
        myTask.execute();

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RecipesResult.class);
        startActivity(intent);
    }

    public static SearchRecipesFragment getFragment()
    {
        Bundle args = new Bundle();
        SearchRecipesFragment fragment = new SearchRecipesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // создаем объект который отображает вышеописанный IP-адрес.
                InetAddress ipAddress = InetAddress.getByName(myConst.SERVER_ADDRESS);
                // создаем сокет используя IP-адрес и порт сервера.
                client = new Socket(myConst.SERVER_ADDRESS, myConst.PORT);//переменная для получение данных

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                InputStream sin = client.getInputStream();
                OutputStream sout = client.getOutputStream();

                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);

                JSONObject jsonOut = new JSONObject();
                jsonOut.put("command", myConst.COMMANDS.get("getAllProducts"));
                outToServer = jsonOut.toString();
                out.writeUTF(outToServer); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                BufferedReader instr = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String str = null;
                while ((str = instr.readLine()) != null) {
                    inputFromServer += str;
                }

            } catch (IOException e) {
                comment = ("Не удалось подключится к " + myConst.SERVER_ADDRESS + ":" + myConst.PORT);
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                JSONObject reader = new JSONObject(inputFromServer);
                JSONArray productsJSON = reader.getJSONArray("products");
                dbHelper.addProducts(productsJSON);
                products = dbHelper.getProducts("соль");

            } catch (JSONException e) {
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }
            catch (Exception e)
            {
                for (StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            productsList = (ListView) view.findViewById(R.id.products_listView);
            sa_editText.setText(products.get(0).getName());
            super.onPostExecute(result);
        }
    }
}
