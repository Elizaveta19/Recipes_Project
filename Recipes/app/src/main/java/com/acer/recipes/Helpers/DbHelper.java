package com.acer.recipes.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.acer.recipes.Recipe;
import com.acer.recipes.RecipeNutrition.Carbs;
import com.acer.recipes.RecipeNutrition.Fat;
import com.acer.recipes.RecipeNutrition.Ingredient;
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
    public static final String YIELD = "yield";
    public static final String IMG_URL = "img_url";

    public static final String INGREDIENTS_TABLE_NAME = "ingredients";
    public static final String ID_RECIPE = "id_recipe";

    public static final String SHOPPING_LIST_TABLE_NAME = "shopping_list";
    public static final String IS_BOUGHT = "is_bought";

    public static final String FAT_TABLE_NAME = "fats";
    public static final String CARBS_TABLE_NAME = "carbs";
    public static final String PROTEIN_TABLE_NAME = "proteins";

    public static final String TOTAL_WEIGHT = "total_weight";
    public static final String TOTAL = "total";
    public static final String DAILY = "daily";

    public static final String SATURATED = "saturated";
    public static final String TRANS = "trans";
    public static final String MONO = "monounsaturated";
    public static final String POLY = "polyunsaturated";

    public static final String CARBS_NET = "carbs_net";
    public static final String FILBER = "filber";
    public static final String SUGARS = "sugars";

    public static final String CREATE_RECIPE_TABLE = "Create table " + RECIPE_TABLE_NAME + " ( "
            + ID + " TEXT PRIMARY KEY, "
            + TITLE + " TEXT, "
            + SOURCE + " TEXT, "
            + CAL + " INTEGER, "
            + WEIGHT + " INTEGER, "
            + YIELD + " INTEGER, "
            + IMG_URL + " TEXT );";

    public static final String CREATE_INGREDIENTS_TABLE = "Create table "
            + INGREDIENTS_TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TITLE + " TEXT, "
            + " FOREIGN KEY (" + ID_RECIPE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

    public static final String CREATE_SHOPPING_LIST_TABLE = "Create table "
            + SHOPPING_LIST_TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TITLE + " TEXT, "
            + IS_BOUGHT + " INTEGER, "
            + " FOREIGN KEY (" + ID_RECIPE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

    public static final String CREATE_FAT_TABLE = "Create table "
            + FAT_TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TOTAL_WEIGHT + " INTEGER, "
            + TOTAL + " INTEGER, "
            + DAILY + " INTEGER, "
            + SATURATED + " INTEGER, "
            + TRANS + " INTEGER, "
            + MONO + " INTEGER, "
            + POLY + " INTEGER, "
            + " FOREIGN KEY (" + ID_RECIPE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

    public static final String CREATE_CARBS_TABLE = "Create table "
            + CARBS_TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TOTAL_WEIGHT + " INTEGER, "
            + TOTAL + " INTEGER, "
            + DAILY + " INTEGER, "
            + CARBS_NET + " INTEGER, "
            + FILBER + " INTEGER, "
            + SUGARS + " INTEGER, "
            + " FOREIGN KEY (" + ID_RECIPE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

    public static final String CREATE_PROTEIN_TABLE = "Create table "
            + PROTEIN_TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_RECIPE + " TEXT, "
            + TOTAL_WEIGHT + " INTEGER, "
            + TOTAL + " INTEGER, "
            + DAILY + " INTEGER, "
            + " FOREIGN KEY (" + ID_RECIPE + ") REFERENCES " + RECIPE_TABLE_NAME+ "(" + ID + "));";

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
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
        db.execSQL(CREATE_FAT_TABLE);
        db.execSQL(CREATE_CARBS_TABLE);
        db.execSQL(CREATE_PROTEIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_SHOPPING_LIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_FAT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_CARBS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_PROTEIN_TABLE);
        onCreate(db);
    }

    public void addRecipe(Recipe recipe)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_1 = new ContentValues();

        //recipe
        values_1.put(ID, recipe.getId());
        values_1.put(TITLE, recipe.getTitle());
        values_1.put(SOURCE, recipe.getUrl());
        values_1.put(CAL, recipe.getCalories());
        values_1.put(WEIGHT, recipe.getTotalWeight());
        values_1.put(YIELD, recipe.getNumberOfPortion());
        String a = recipe.getImgUrl();
        values_1.put(IMG_URL, a);
        db.insert(RECIPE_TABLE_NAME, null, values_1);

        //ingredients
        ContentValues values_2 = new ContentValues();
        for(int i = 0; i < recipe.getIngredients().size(); i++) {
            values_2.put(ID_RECIPE, recipe.getId());
            values_2.put(TITLE, recipe.getIngredients().get(i));
            db.insert(INGREDIENTS_TABLE_NAME, null, values_2);
        }

        //fats
        ContentValues values_3 = new ContentValues();
        values_3.put(ID_RECIPE, recipe.getId());
        values_3.put(TOTAL_WEIGHT, recipe.getFat().getTotalWeight());
        values_3.put(TOTAL, recipe.getFat().getTotalPerRecipe());
        values_3.put(DAILY, recipe.getFat().getDailyPerRecipe());
        values_3.put(SATURATED, recipe.getFat().getSaturatedPerRecipe());
        values_3.put(TRANS, recipe.getFat().getTransPerRecipe());
        values_3.put(MONO, recipe.getFat().getMonoPerRecipe());
        values_3.put(POLY, recipe.getFat().getPolyPerRecipe());
        db.insert(FAT_TABLE_NAME, null, values_3);

        //carbs
        ContentValues values_4 = new ContentValues();
        values_4.put(ID_RECIPE, recipe.getId());
        values_4.put(TOTAL_WEIGHT, recipe.getCarbs().getTotalWeight());
        values_4.put(TOTAL, recipe.getCarbs().getTotalPerRecipe());
        values_4.put(DAILY, recipe.getCarbs().getDailyPerRecipe());
        values_4.put(CARBS_NET, recipe.getCarbs().getCarbsNetPerRecipe());
        values_4.put(FILBER, recipe.getCarbs().getFilberPerRecipe());
        values_4.put(SUGARS, recipe.getCarbs().getSugarsPerRecipe());
        db.insert(CARBS_TABLE_NAME, null, values_4);

        //proteins
        ContentValues values_5 = new ContentValues();
        values_5.put(ID_RECIPE, recipe.getId());
        values_5.put(TOTAL_WEIGHT, recipe.getProtein().getTotalWeight());
        values_5.put(TOTAL, recipe.getProtein().getTotalPerRecipe());
        values_5.put(DAILY, recipe.getProtein().getDailyPerRecipe());
        db.insert(PROTEIN_TABLE_NAME, null, values_5);

        db.close();

    }

    public void deleteRecipe(Recipe recipe)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] { String.valueOf(recipe.getId())};

        db.delete(RECIPE_TABLE_NAME, ID + "=?", whereArgs);
        db.delete(INGREDIENTS_TABLE_NAME, ID_RECIPE + "=?", whereArgs);
        db.delete(FAT_TABLE_NAME, ID_RECIPE + "=?", whereArgs);
        db.delete(CARBS_TABLE_NAME, ID_RECIPE + "=?", whereArgs);
        db.delete(PROTEIN_TABLE_NAME, ID_RECIPE + "=?", whereArgs);

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
                String fatSelectQuery = "SELECT * FROM " + FAT_TABLE_NAME + " WHERE id_recipe='" + recipeCursor.getString(0) + "'";
                String carbsSelectQuery = "SELECT * FROM " + CARBS_TABLE_NAME + " WHERE id_recipe='" + recipeCursor.getString(0) + "'";
                String proteinsSelectQuery = "SELECT * FROM " + PROTEIN_TABLE_NAME + " WHERE id_recipe='" + recipeCursor.getString(0) + "'";
                Cursor ingredientCursor = db.rawQuery(ingredientsSelectQuery, null);
                ArrayList<String> ingredientsList = new ArrayList<>();
                if(ingredientCursor.moveToFirst()) {
                    do {
                        ingredientsList.add(ingredientCursor.getString(2));
                    } while (ingredientCursor.moveToNext());
                }

                Fat fat;
                Cursor fatCursor = db.rawQuery(fatSelectQuery, null);
                if(fatCursor.moveToFirst()) {
                    int totalWeight = fatCursor.getInt(2);
                    int total = fatCursor.getInt(3);
                    int daily = fatCursor.getInt(4);
                    int saturated = fatCursor.getInt(5);
                    int trans = fatCursor.getInt(6);
                    int monounsaturated = fatCursor.getInt(7);
                    int polyunsaturated = fatCursor.getInt(8);
                    fat = new Fat(totalWeight, total, daily, saturated, trans, monounsaturated, polyunsaturated);
                    //fat = new Fat(fatCursor.getInt(2), fatCursor.getInt(3), fatCursor.getInt(4), fatCursor.getInt(5), fatCursor.getInt(6), fatCursor.getInt(7), fatCursor.getInt(8));
                }
                else{
                    fat = new Fat();
                }

                Carbs carbs;
                Cursor carbsCursor = db.rawQuery(carbsSelectQuery, null);
                if(carbsCursor.moveToFirst()) {
                    carbs = new Carbs(carbsCursor.getInt(2), carbsCursor.getInt(3), carbsCursor.getInt(4), carbsCursor.getInt(5), carbsCursor.getInt(6), carbsCursor.getInt(7));
                }
                else{
                    carbs = new Carbs();
                }

                Protein protein;
                Cursor proteinCursor = db.rawQuery(proteinsSelectQuery, null);
                if(proteinCursor.moveToFirst()) {
                    protein = new Protein(proteinCursor.getInt(2), proteinCursor.getInt(3), proteinCursor.getInt(4));
                }
                else {
                    protein = new Protein();
                }

                Recipe recipe = new Recipe(recipeCursor.getString(0), recipeCursor.getString(1), ingredientsList, recipeCursor.getString(2), recipeCursor.getInt(3), recipeCursor.getInt(4), recipeCursor.getInt(5), recipeCursor.getString(6), fat, carbs, protein, true);
                recipesList.add(recipe);
                ingredientCursor.close();
            }while (recipeCursor.moveToNext());

        }
        recipeCursor.close();

        return recipesList;
    }

    public void addIngredientToShoppingList(Recipe recipe, String ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_1 = new ContentValues();

        values_1.put(ID_RECIPE, recipe.getId());
        values_1.put(TITLE, ingredient);
        values_1.put(IS_BOUGHT, 0);
        db.insert(SHOPPING_LIST_TABLE_NAME, null, values_1);

        db.close();

    }

    public void deleteIngredientFromShoppingList(String ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String ingredientsSelectQuery = "";
        if (ingredient.contains("\"")){
            ingredientsSelectQuery = "SELECT * FROM " + SHOPPING_LIST_TABLE_NAME + " WHERE " + TITLE + "=" + "'" + ingredient + "'";
        }
        else {
            ingredientsSelectQuery = "SELECT * FROM " + SHOPPING_LIST_TABLE_NAME + " WHERE " + TITLE + "=" + "\"" + ingredient + "\"";
        }

        Cursor ingredientCursor = db.rawQuery(ingredientsSelectQuery, null);
        if(ingredientCursor.moveToFirst()) {
            do {
                int ingredient_id = ingredientCursor.getInt(0);
                String where = ID + "=" + ingredient_id;
                db.delete(SHOPPING_LIST_TABLE_NAME, where, null);
            } while (ingredientCursor.moveToNext());
        }
        ingredientCursor.close();
        db.close();

    }

    public void setBoughtIngredient(Ingredient ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_1 = new ContentValues();
        values_1.put(IS_BOUGHT, 1);
        String where = ID + "=" + ingredient.getId();
        db.update(SHOPPING_LIST_TABLE_NAME, values_1, where, null);

        db.close();

    }

    public boolean isInShoppingList(Recipe recipe, String ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String ingredientsSelectQuery = "";
        if (ingredient.contains("\"")){
            ingredientsSelectQuery = "SELECT * FROM " + SHOPPING_LIST_TABLE_NAME + " WHERE " + TITLE + "=" + "'" + ingredient + "' AND " + ID_RECIPE + "=" + "'" + recipe.getId() + "'";
        }
        else {
            ingredientsSelectQuery = "SELECT * FROM " + SHOPPING_LIST_TABLE_NAME + " WHERE " + TITLE + "=" + "\"" + ingredient + "\" AND " + ID_RECIPE + "=" + "\"" + recipe.getId() + "\"";
        }
        Cursor ingredientCursor = db.rawQuery(ingredientsSelectQuery, null);
        ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
        if(ingredientCursor.moveToFirst()) {
            do {
                ingredientsList.add(new Ingredient(ingredientCursor.getInt(0), ingredientCursor.getString(2), ingredientCursor.getInt(3)));
            } while (ingredientCursor.moveToNext());
        }

        ingredientCursor.close();
        db.close();

        if(ingredientsList.isEmpty())
            return false;
        else
            return true;


    }

    public void unsetBoughtIngredient(Ingredient ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_1 = new ContentValues();
        values_1.put(IS_BOUGHT, 0);
        String where = ID + "=" + ingredient.getId();
        db.update(SHOPPING_LIST_TABLE_NAME, values_1, where, null);

        db.close();

    }

    public ArrayList<Ingredient> getShoppingList()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String ingredientsSelectQuery = "SELECT * FROM " + SHOPPING_LIST_TABLE_NAME;
        Cursor ingredientCursor = db.rawQuery(ingredientsSelectQuery, null);
        ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();
        if(ingredientCursor.moveToFirst()) {
            do {
                ingredientsList.add(new Ingredient(ingredientCursor.getInt(0), ingredientCursor.getString(2), ingredientCursor.getInt(3)));
            } while (ingredientCursor.moveToNext());
        }

        ingredientCursor.close();

        return ingredientsList;
    }

}
