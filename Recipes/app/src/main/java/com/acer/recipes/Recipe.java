package com.acer.recipes;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{
    private int id;
    private String title;
    private String ingredients;
    private String text;
    private int ccal;
    private int totalWeight;
    private String imgUrl;

    public Recipe(int _id, String _title, String _ingredients, String _text, int _ccal, int _totalWeight, String _imgUrl)
    {
        id = _id;
        title = _title;
        ingredients = _ingredients;
        text = _text;
        ccal = _ccal;
        totalWeight = _totalWeight;
        imgUrl = _imgUrl;
    }

    public Recipe(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        id = Integer.parseInt(data[0]);
        title = data[1];
        ingredients = data[2];
        text = data[3];
        ccal = Integer.parseInt(data[4]);
        totalWeight = Integer.parseInt(data[5]);
        imgUrl = data[6];
    }

    public int getId() { return id; }

    public String getTitle(){ return title; }

    public String getIngredients() { return ingredients; }

    public String getText() { return text;  }

    public int getCcal(){ return ccal; }

    public int getTotalWeight() { return totalWeight; }

    public String getImgUrl() { return imgUrl; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(id), title, ingredients, text, String.valueOf(ccal), String.valueOf(totalWeight), imgUrl });
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