package com.example.androidjava.home.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.R;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>  {

private Context context;
private List<Meal> meals;
private OnCountryClickListener listener;
//private OnFavProductListener listener;
public AreaAdapter(Context context, List<Meal> meals, OnCountryClickListener listener){
	this.listener =listener;
    this.context=context;
    this.meals=meals;
}
@NonNull
@Override
public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.meal_cell, parent, false);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, int position) {
	Meal meal = meals.get(position);
	holder.title.setText(meal.getStrArea());
	///holder.description.setText(meal.ge);
	holder.itemView.setOnClickListener(v -> {
		if (listener != null) {
			listener.onCountryClick(meal.getStrArea());
		}
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
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		thumbnail = itemView.findViewById(R.id.itemImg);
		title = itemView.findViewById(R.id.Title);
		description = itemView.findViewById(R.id.desc);
	
		
	}
}



}
