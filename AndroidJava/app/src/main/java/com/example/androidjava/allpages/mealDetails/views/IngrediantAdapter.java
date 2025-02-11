package com.example.androidjava.allpages.mealDetails.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidjava.R;

import java.util.Map;

public class IngrediantAdapter extends RecyclerView.Adapter<IngrediantAdapter.ViewHolder> {
private Context context;
private Map<String, String> ingredientsDetails;
//private com.example.androidjava.listeners.OnIngredientClickListener listener;

public IngrediantAdapter(Context context, Map<String, String> ingredients) {
	this.context = context;
	this.ingredientsDetails = ingredients;

}

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
	String ingredient = (String) ingredientsDetails.keySet().toArray()[position];
	String thumbnailUrl = ingredientsDetails.get(ingredient);
	
	Log.i("1", "Ingredient: (" + ingredient + ") Image URL: " + thumbnailUrl);
	
	holder.ingredientName.setText(ingredient);
	

		Glide.with(context)
				.load(thumbnailUrl)
				.into(holder.ingredientImg);

	
	
	
}

@Override
public int getItemCount() {
	return ingredientsDetails != null ? ingredientsDetails.size() : 0;
}

public static class ViewHolder extends RecyclerView.ViewHolder {
	TextView ingredientName;
	ImageView ingredientImg;
	
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		ingredientName = itemView.findViewById(R.id.stepTxt);
		ingredientImg = itemView.findViewById(R.id.ingredientImg);
	}
}


}
