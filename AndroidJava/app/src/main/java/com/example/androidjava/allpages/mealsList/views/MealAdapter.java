package com.example.androidjava.allpages.mealsList.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

private Context context;
private List<Meal> meals;
private OnMealClickListener listener;
private int selectedPosition = -1; // No day selected initially



public MealAdapter(Context context, List<Meal> meals,OnMealClickListener listener){
	this.context=context;
	this.listener=listener;
	this.meals=meals;
}
@NonNull
@Override
public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.meal_cell, parent, false);
	return new MealAdapter.ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
	Meal meal = meals.get(position);
	holder.title.setText(meal.getStrMeal());
	
	// Change background based on selection
	if (position == selectedPosition) {
		holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.SoftPink)); // Selected
	} else {
		holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white)); // Default
	}
	
	holder.itemView.setOnClickListener(v -> {
		selectedPosition = position;  // Update selected position
		notifyDataSetChanged();  // Refresh the list to update colors
	});
	
	holder.thumbnail.setOnClickListener(v -> listener.onMealClick(meal));
	holder.heartImg.setOnClickListener(v -> {
		listener.onFavClick(meal);
		holder.isFav = !holder.isFav;
		holder.heartImg.setImageResource(holder.isFav ? R.drawable.fav : R.drawable.notfav);
	});
	
	Glide.with(context)
			.load(meal.getStrMealThumb())
			.into(holder.thumbnail);
}


@Override
public int getItemCount() {
	
	
	return (meals != null) ? meals.size() : 0;
}
public static class ViewHolder extends RecyclerView.ViewHolder{
	public ImageView thumbnail;
	public TextView title, description;
	ImageView heartImg;
	public boolean isFav = false;
	
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		thumbnail = itemView.findViewById(R.id.itemImg);
		title = itemView.findViewById(R.id.Title);
		//description = itemView.findViewById(R.id.desc);
		heartImg=itemView.findViewById(R.id.heartImg);
	}
}
}