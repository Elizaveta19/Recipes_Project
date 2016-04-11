package com.acer.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecipeViewHolder> {//implements View.OnClickListener{

    private final LayoutInflater inflater;
    private final Activity mActivity;
    Context currentContext;
    ArrayList<Recipe> recipes;

    public RVAdapter(Context context, Activity mActivity, ArrayList<Recipe> recipes){
        inflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.mActivity = mActivity;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        RecipeViewHolder pvh = new RecipeViewHolder(v);
        currentContext = viewGroup.getContext();

        return pvh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder recipeViewHolder, final int position) {

        Picasso.with(currentContext).load(recipes.get(position).getImgUrl()).into(recipeViewHolder.recipePhoto);
        recipeViewHolder.recipeTitle.setText(recipes.get(position).getTitle());
        recipeViewHolder.recipeTotalWeight.setText("Total Weight:" + Integer.toString(recipes.get(position).getTotalWeight()) + "g (" + Integer.toString(recipes.get(position).getNumberOfPortion()) + " portions)");
        recipeViewHolder.recipeCcal.setText("Calories pro 100g: " + Integer.toString(recipes.get(position).getStandartCalories()));
        recipeViewHolder.recipeCcalProPerson.setText("Calories pro portion: " + Integer.toString(recipes.get(position).getCaloriesProPerson()) + " (" +Integer.toString(recipes.get(position).getGrammProPerson()) + " g)");
        recipeViewHolder.favorite.setChecked(recipes.get(position).isFavorite());

        recipeViewHolder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Constants myConst = new Constants();
                try {
                    if(isChecked) {
                        recipes.get(position).setFavorite();
                        myConst.dbHelper.addRecipe(recipes.get(position));
                    }
                    else {
                        recipes.get(position).unsetFavorite();
                        myConst.dbHelper.deleteRecipe(recipes.get(position));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    for (StackTraceElement ste : e.getStackTrace())
                        Log.v("Ошибка============", ste.toString());
                }
            }
        });

        recipeViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RecipeActivity.class);
                intent.putExtra("recipe", recipes.get(position));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView recipeTitle;
        TextView recipeTotalWeight;
        TextView recipeCcal;
        TextView recipeCcalProPerson;
        ImageView recipePhoto;
        CheckBox favorite;
        RecipeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_title);
            recipeTotalWeight = (TextView)itemView.findViewById(R.id.recipe_total_weight);
            recipeCcal = (TextView)itemView.findViewById(R.id.recipe_ccal);
            recipeCcalProPerson = (TextView)itemView.findViewById(R.id.recipe_ccal_pro_person);
            recipePhoto = (ImageView)itemView.findViewById(R.id.recipe_photo);
            favorite = (CheckBox) itemView.findViewById(R.id.favorite_button);

        }
    }
}
