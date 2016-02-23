package com.acer.recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecipeViewHolder>{

    ArrayList<Recipe> recipes;
    public RVAdapter(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        RecipeViewHolder pvh = new RecipeViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder recipeViewHolder, int position) {


        recipeViewHolder.recipeTitle.setText(recipes.get(position).getTitle());
        recipeViewHolder.recipeTotalWeight.setText("Total Weight:" + Integer.toString(recipes.get(position).getTotalWeight()));
        recipeViewHolder.recipeCcal.setText("Calories: " + Integer.toString(recipes.get(position).getCcal()));
        recipeViewHolder.recipePhoto.setImageURI(Uri.parse(recipes.get(position).getImgUrl()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setRecipesList(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView recipeTitle;
        TextView recipeTotalWeight;
        TextView recipeCcal;
        ImageView recipePhoto;
        RecipeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_title);
            recipeTotalWeight = (TextView)itemView.findViewById(R.id.recipe_total_weight);
            recipeCcal = (TextView)itemView.findViewById(R.id.recipe_ccal);
            recipePhoto = (ImageView)itemView.findViewById(R.id.recipe_photo);
        }
    }
}
