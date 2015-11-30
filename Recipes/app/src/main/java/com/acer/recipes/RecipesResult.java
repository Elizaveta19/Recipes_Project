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
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    String address2 = "healthrecipes/index.php";
    String comment = null;
    MyTask myTask;

    private static final int LAYOUT = R.layout.activity_recipes_result;
    private static final String ServerAddressDDNS = "healthrecipes.ddns.net:80";
    private static final String phpAddress = "/get_details.php?product_id=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_result);
        myTask = new MyTask();
        myTask.execute();

        TextView textView = (TextView) findViewById(R.id.arr_name);
    }

    class MyTask extends AsyncTask<Void, Void, Void>
    {

        String title;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // создаем объект который отображает вышеописанный IP-адрес.
                InetAddress ipAddress = InetAddress.getByName(address);
                // создаем сокет используя IP-адрес и порт сервера.
                client = new Socket(address, port);//переменная для получение данных

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                InputStream sin = client.getInputStream();
                OutputStream sout = client.getOutputStream();

                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);

                String line = "It's the Client";
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                /*String line2 = in.readUTF(); // ждем пока сервер отошлет строку текста.
                comment = line2;*/
                BufferedReader instr = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String str = "";
                while((str = instr.readLine()) != null) {
                    comment = str;
                }



            } catch (IOException e) {
                comment = ("Не удалось подключится к " + address + ":" + port);
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
