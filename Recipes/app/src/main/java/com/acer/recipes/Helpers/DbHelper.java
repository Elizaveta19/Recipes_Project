package com.acer.recipes.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.acer.recipes.Product;
import com.acer.recipes.Recipe;
import com.acer.recipes.RecipeNutrition.Carbs;
import com.acer.recipes.RecipeNutrition.Fat;
import com.acer.recipes.RecipeNutrition.Protein;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "DataBase";

    public static final String RECIPE_TABLE_NAME = "recipes";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String SOURCE = "source";
    public static final String CAL = "cal";
    public static final String WEIGHT = "weight";
    public static final String IMG_URL = "img_url";

    private ArrayList<String> ingredients;
    public static final String INGREDIENTS_TABLE_NAME = "ingredients";
    public static final String ID_INGREDIENT = "_id";
    public static final String ID_RECIPE = "id_recipe";

    private Fat fat;
    private Carbs carbs;
    private Protein protein;

    public static final String CREATE_RECIPE_TABLE = "Create table " + RECIPE_TABLE_NAME + " ( "
            + ID + " TEXT PRIMARY KEY, "
            + TITLE + " TEXT, "
            + SOURCE + " TEXT, "
            + CAL + " INTEGER, "
            + WEIGHT + " INTEGER, "
            + IMG_URL + " TEXT );";

    public static final String CREATE_INGREDIENTS_TABLE = "Create table "
            + INGREDIENTS_TABLE_NAME + " ( "
            + ID_INGREDIENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TITLE + " TEXT, "
            + " FOREIGN KEY (" + TITLE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    public void createTables(SQLiteDatabase db) {

        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE_NAME);
        onCreate(db);
    }

    public void addRecipe(Recipe recipe)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_1 = new ContentValues();

        values_1.put(ID, recipe.getId());
        values_1.put(TITLE, recipe.getTitle());
        values_1.put(SOURCE, recipe.getUrl());
        values_1.put(CAL, recipe.getCalories());
        values_1.put(WEIGHT, recipe.getTotalWeight());
        String a = recipe.getImgUrl();
        values_1.put(IMG_URL, a);
        db.insert(RECIPE_TABLE_NAME, null, values_1);

        ContentValues values_2 = new ContentValues();
        for(int i = 0; i < recipe.getIngredients().size(); i++) {
            values_2.put(ID_RECIPE, recipe.getId());
            values_2.put(TITLE, recipe.getIngredients().get(i));
            db.insert(INGREDIENTS_TABLE_NAME, null, values_2);
        }

        db.close();

    }

    public ArrayList<Recipe> getAllRecipes()
    {
        ArrayList<Recipe> recipesList = new ArrayList<Recipe>();
        String recipeSelectQuery = "SELECT * FROM " + RECIPE_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor recipeCursor = db.rawQuery(recipeSelectQuery, null);
        if(recipeCursor.moveToFirst()) {
            do {
                String ingredientsSelectQuery = "SELECT * FROM " + INGREDIENTS_TABLE_NAME + " WHERE id_recipe='" + recipeCursor.getString(0) + "'";
                Cursor ingredientCursor = db.rawQuery(ingredientsSelectQuery, null);
                ArrayList<String> ingredientsList = new ArrayList<String>();
                if(ingredientCursor.moveToFirst()) {
                    do {
                        ingredientsList.add(ingredientCursor.getString(2));
                    } while (ingredientCursor.moveToNext());
                }
                int cal = recipeCursor.getInt(3);
                Recipe recipe = new Recipe(recipeCursor.getString(0), recipeCursor.getString(1), ingredientsList, recipeCursor.getString(2), cal, recipeCursor.getInt(4), recipeCursor.getString(5), new Fat(), new Carbs(), new Protein(), true);
                recipesList.add(recipe);
                ingredientCursor.close();
            }while (recipeCursor.moveToNext());

        }
        recipeCursor.close();

        return recipesList;
    }

    /*public Product getProductByName(String _name)
    {
        String selectQuery = "SELECT * FROM " + RECIPE_TABLE_NAME+ " WHERE name='" + _name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2));
            return product;
        }

        return null;
    }*/

}
