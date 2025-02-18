package com.example.androidjava.allpages.home.views;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>  {

private Context context;
private List<Meal> meals;
private OnClickListener listener;
//private OnFavProductListener listener;
public AreaAdapter(Context context, List<Meal> meals, OnClickListener listener){
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
	holder.heartImg.setVisibility(View.GONE);
	holder.thumbnail.setOnClickListener(v -> {
			listener.onCountryClick(meal.getStrArea());
	});
	
	String flagUrl = getCountryFlagUrl(meal.getStrArea());
	
	Glide.with(context)
			.load(flagUrl)
			.into(holder.thumbnail);
	
}
private String getCountryFlagUrl(String countryName) {
	Map<String, String> countryCodes = new HashMap<>();
	
	countryCodes.put("American", "us");
	countryCodes.put("British", "gb");
	countryCodes.put("Canadian", "ca");
	countryCodes.put("Chinese", "cn");
	countryCodes.put("Croatian", "hr");
	countryCodes.put("Dutch", "nl");
	countryCodes.put("Egyptian", "eg");
	countryCodes.put("Filipino", "ph");
	countryCodes.put("French", "fr");
	countryCodes.put("Greek", "gr");
	countryCodes.put("Indian", "in");
	countryCodes.put("Irish", "ie");
	countryCodes.put("Italian", "it");
	countryCodes.put("Jamaican", "jm");
	countryCodes.put("Japanese", "jp");
	countryCodes.put("Kenyan", "ke");
	countryCodes.put("Malaysian", "my");
	countryCodes.put("Mexican", "mx");
	countryCodes.put("Moroccan", "ma");
	countryCodes.put("Norwegian", "no");
	countryCodes.put("Polish", "pl");
	countryCodes.put("Portuguese", "pt");
	countryCodes.put("Russian", "ru");
	countryCodes.put("Spanish", "es");
	countryCodes.put("Thai", "th");
	countryCodes.put("Tunisian", "tn");
	countryCodes.put("Turkish", "tr");
	countryCodes.put("Ukrainian", "ua");
	countryCodes.put("Uruguayan", "uy");
	countryCodes.put("Vietnamese", "vn");
	
	String countryCode = countryCodes.getOrDefault(countryName, "xx");
	return "https://flagcdn.com/w320/" + countryCode + ".png";
}

@Override
public int getItemCount() {
	return (meals != null) ? meals.size() : 0;
}
public static class ViewHolder extends RecyclerView.ViewHolder{
	public ImageView thumbnail,heartImg;
	public TextView title, description;
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		thumbnail = itemView.findViewById(R.id.itemImg);
		title = itemView.findViewById(R.id.Title);
		heartImg=itemView.findViewById(R.id.heartImg);
	}
}



}
