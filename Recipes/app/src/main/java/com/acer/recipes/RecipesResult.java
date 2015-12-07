package com.acer.recipes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import com.loopj.android.http.*;

public class RecipesResult extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_recipes_result;

    Socket client = null;
    String comment = new String();
    String inputFromServer = new String();
    String outToServer = new String();

    public static final Constants myConst = new Constants();
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        myTask = new MyTask();
        myTask.execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {
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
                jsonOut.put("command", "1");
                jsonOut.put("id", "3");
                outToServer = jsonOut.toString();
                //outToServer = "{\"command:\":\"1\", \"id\":\"3\"}";
                //Log.v("Строка============", outToServer);
                out.writeUTF(outToServer); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                /*String line2 = in.readUTF(); // ждем пока сервер отошлет строку текста.
                comment = line2;*/
                BufferedReader instr = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String str = null;
                while((str = instr.readLine()) != null) {
                    inputFromServer += str;
                }

            } catch (IOException e) {
                comment = ("Не удалось подключится к " + myConst.SERVER_ADDRESS + ":" + myConst.SERVER_ADDRESS);
                for(StackTraceElement ste : e.getStackTrace())
                    Log.v("Ошибка============", ste.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                //String a = "{\"success\":1,\"product\":[{\"product_id\":\"3\",\"name\":\"Яблоко\",\"ccal\":\"53\"}]}";
                JSONObject reader = new JSONObject(inputFromServer);
                JSONArray product = reader.getJSONArray("product");

                for(int i = 0; i < product.length(); i++)
                {
                    JSONObject jsonObject = product.getJSONObject(i);

                    int  id_product = Integer.parseInt(jsonObject.getString("id_product").toString());
                    String name = jsonObject.getString("name").toString();
                    int ccal = Integer.parseInt(jsonObject.getString("ccal").toString());

                    comment = String.valueOf(id_product) + "   "  + name + "   " + String.valueOf(ccal);
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
            TextView textView = (TextView) findViewById(R.id.arr_name);
            super.onPostExecute(result);
            textView.setText(comment);
        }
    }
}
