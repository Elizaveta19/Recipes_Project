package com.acer.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "DataBase";

    public static final String TABLE_NAME = "products";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CCAL = "ccal";
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + " ( " + ID
            + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + CCAL + " FLOAT );";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    public void createTables(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProducts(JSONArray jsonProducts)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for(int i = 0; i < jsonProducts.length(); i++)
            {
                JSONObject jsonObject = jsonProducts.getJSONObject(i);

                int id_product = Integer.parseInt(jsonObject.getString("id_product").toString());
                String name = jsonObject.getString("name").toString();
                float ccal = Float.parseFloat(jsonObject.getString("ccal").toString());

                values.put(ID, id_product);
                values.put(NAME, name);
                values.put(CCAL, ccal);
                db.insert(TABLE_NAME, null, values);
            }

            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
            for(StackTraceElement ste : e.getStackTrace())
                Log.v("Ошибка============", ste.toString());
        }
    }

    public ArrayList<Product> getProducts(String subStr)
    {
        ArrayList<Product> productList = new ArrayList<Product>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE name like '%" + subStr + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do {
                Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2));
                productList.add(product);
            }while (cursor.moveToNext());
        }

        return productList;
    }

    public Product getProductByName(String _name)
    {
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE name='" + _name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2));
            return product;
        }

        return null;
    }

}