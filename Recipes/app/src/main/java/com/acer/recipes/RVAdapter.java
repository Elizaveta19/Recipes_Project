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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.recipes.Fragments.RecipeFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecipeViewHolder> {//implements View.OnClickListener{

    private final LayoutInflater inflater;
    private final Activity mActivity;
    Context currentContext;
    ArrayList<Recipe> recipes;
    ImageLoader imageLoader;

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
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(currentContext));

        return pvh;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder recipeViewHolder, final int position) {

        try {
            //if (recipeViewHolder.recipePhoto.getDrawable()==null){
                imageLoader.displayImage(recipes.get(position).getImgUrl(), recipeViewHolder.recipePhoto);
           // }

        }
        catch(Exception e){
            e.printStackTrace();
            for (StackTraceElement ste : e.getStackTrace())
                Log.v("Ошибка============", ste.toString());
        }

        recipeViewHolder.recipeTitle.setText(recipes.get(position).getTitle());
        recipeViewHolder.recipeTotalWeight.setText("Total Weight:" + Integer.toString(recipes.get(position).getTotalWeight()));
        recipeViewHolder.recipeCcal.setText("Calories: " + Integer.toString(recipes.get(position).getCalories()));

        recipeViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants myConst = new Constants();
                try {
                    myConst.dbHelper.addRecipe(recipes.get(position));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    for (StackTraceElement ste : e.getStackTrace())
                        Log.v("Ошибка============", ste.toString());
                }
                Intent intent = new Intent(mActivity, RecipeFragment.class);
                intent.putExtra("recipe", recipes.get(position));
                mActivity.startActivity(intent);
            }
        });
        //recipeViewHolder.recipePhoto.setImageURI(Uri.parse(recipes.get(position).getImgUrl()));
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

    /*@Override
    public void onClick(View v) {

        Intent likeWindow = new Intent(currentContext, RecipeFragment.class);
        Bundle bundle = new Bundle();
        //bundle.putSerializable("user", fragment.user);
        bundle.putString("url",recipes.get(position).getRecipeUrl());
        likeWindow.putExtras(bundle);
        currentContext.startActivity(likeWindow);

        //int itemPosition = mRecyclerView.getChildPosition(view);
        //String item = mList.get(itemPosition);
    }*/

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
