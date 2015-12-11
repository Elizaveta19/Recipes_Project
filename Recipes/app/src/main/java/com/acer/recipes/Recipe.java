package com.acer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{
    private int id;
    private String title;
    private String ingredients;
    private String text;
    private float ccal;
    private String time;

    public Recipe(int _id, String _title, String _ingredients, String _text, float _ccal, String _time)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        text = _text;
        ccal = _ccal;
        time = _time;
    }

    public Recipe(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        id = Integer.parseInt(data[0]);
        title = data[1];
        ingredients = data[2];
        text = data[3];
        ccal = Float.parseFloat(data[4]);
        time = data[5];
    }

    public int getId() { return id; }

    public String getTitle(){ return title; }

    public String getIngredients() { return ingredients; }

    public String getText() { return text;  }

    public float getCcal(){ return ccal; }

    public String getTime() { return time; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(id), title, ingredients, text, String.valueOf(ccal), time });
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
        @Override
        public Recipe createFromParcel(Parcel sourse) {
            return new Recipe(sourse);
        }

        @Override
        public Recipe[] newArray(int size){
            return new Recipe[size];
        }
    };
}