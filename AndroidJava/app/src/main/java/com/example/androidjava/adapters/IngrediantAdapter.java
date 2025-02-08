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
import com.example.androidjava.R;
import com.example.androidjava.listeners.OnIngredientClickListener;

import java.util.List;

public class IngrediantAdapter extends RecyclerView.Adapter<IngrediantAdapter.ViewHolder> {
private Context context;
private List<String> ingredients;
private OnIngredientClickListener listener;

public IngrediantAdapter(Context context, List<String> ingredients, OnIngredientClickListener listener) {
	this.context = context;
	this.ingredients = ingredients;
	this.listener = listener;
	
}

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
	String ingredient = ingredients.get(position);
	holder.ingredientName.setText(ingredient);
	holder.itemView.setOnClickListener(v -> listener.onIngredientClick(ingredient));
	Glide.with(context).load(R.drawable.food).into(holder.ingredientImg);
}

@Override
public int getItemCount() {
	return ingredients.size();
}

public static class ViewHolder extends RecyclerView.ViewHolder {
	TextView ingredientName;
	ImageView ingredientImg;
	
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		ingredientName = itemView.findViewById(R.id.ingredientName);
		ingredientImg = itemView.findViewById(R.id.ingredientImg);
	}
}
}
