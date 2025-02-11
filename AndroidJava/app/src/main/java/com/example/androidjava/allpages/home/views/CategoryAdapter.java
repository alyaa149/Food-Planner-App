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
import com.example.androidjava.Models.Category;
import com.example.androidjava.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

private Context context;
private List<Category>categories;

private OnClickListener listener;
public CategoryAdapter(Context context, List<Category> categories, OnClickListener listener){
	this.listener = listener;
this.categories =categories;
this.context =context;
}
@NonNull
@Override
public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(context).inflate(R.layout.meal_cell, parent, false);
	return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
	Category category = categories.get(position);
	holder.title.setText(category.getStrCategory());
	holder.description.setText("");
     holder.heartImg.setVisibility(View.GONE);
	Glide.with(context)
			.load(category.getStrCategoryThumb())
			.into(holder.thumbnail);
	holder.thumbnail.setOnClickListener(v -> listener.onCategoryListener(category));

}


@Override
public int getItemCount() {
	return (categories != null) ? categories.size() : 0;
}
public static class ViewHolder extends RecyclerView.ViewHolder{
	public ImageView thumbnail, heartImg;
	public TextView title, description;
	
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		thumbnail = itemView.findViewById(R.id.itemImg);
		title = itemView.findViewById(R.id.Title);
		description = itemView.findViewById(R.id.desc);
		heartImg=itemView.findViewById(R.id.heartImg);

		
	}
}



}
