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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import com.loopj.android.http.*;

public class RecipesResult extends AppCompatActivity {

    Socket client = null;
    int port = 8888;
    String address = "192.168.1.35";
    String comment = null;

    private static final int LAYOUT = R.layout.activity_recipes_result;
    // private static final String ServerAddressDDNS = "healthrecipes.ddns.net:80";
    private static final String ServerAddress = "192.168.1.35";
    private static final String phpAddress = "/get_details.php?product_id=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_result);
        Connect();
        //TextView textView = (TextView) findViewById(R.id.arr_name);
        //textView.setText(comment);
    }

    void Connect() {
        //открываю новый поток
        Thread myThread = new Thread(new Runnable() {
            @Override
            //то что должно запускаться в новом потоке
            public void run() {
                try {
                    // создаем объект который отображает вышеописанный IP-адрес.
                    InetAddress ipAddress = InetAddress.getByName(address);
                    // создаем сокет используя IP-адрес и порт сервера.
                    client = new Socket(ipAddress, port);
                    //переменная для получение данных
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //переменная для отправки данных
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    comment = ("Успешное подключение к " + address + ":" + port + "...");
                } catch (IOException e) {
                    comment = ("Не удалось подключится к " + address + ":" + port);
                }
                TextView textView = (TextView) findViewById(R.id.arr_name);
                textView.setText(comment);
            }
        });
        myThread.start(); //запуск нового потока
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
