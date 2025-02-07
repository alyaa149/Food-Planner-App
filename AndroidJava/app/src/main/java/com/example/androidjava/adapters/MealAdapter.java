package com.example.androidjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

private Context context;
private List<Meal> meals;
//private OnFavProductListener listener;
public MealAdapter(Context context, List<Meal> meals){
this.context=context;
this.meals=meals;
}
@NonNull
@Override
public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.meal_cell, parent, false);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
	Meal meal = meals.get(position);
	holder.title.setText(meal.getStrArea());
	///holder.description.setText(meal.ge);
	
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
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		thumbnail = itemView.findViewById(R.id.itemImg);
		title = itemView.findViewById(R.id.Title);
		description = itemView.findViewById(R.id.desc);
	
		
	}
}



}
