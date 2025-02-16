package com.example.androidjava.allpages.plan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.R;
import com.example.androidjava.allpages.mealsList.views.MealAdapter;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
private Context context;
private List<String> days;
OnDayClickListener listener;
	
	public PlanAdapter(Context context, List<String> days,OnDayClickListener listener) {
		this.context = context;
		this.days = days;
		this.listener=listener;
		
	}
@NonNull
@Override
public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
	return new PlanAdapter.ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {
		String day = days.get(position);
	
	holder.dayTextView.setText(day);
	holder.itemView.setOnClickListener(v->listener.onDayClick(Integer.parseInt(day.split("/")[0]),Integer.parseInt(day.split("/")[1]),Integer.parseInt("20"+day.split("/")[2])));
	holder.monthTextView.setText(day.split("/")[0]);
	//Meal meal = plannedMeal.getMeal();
	
	//MealAdapter mealAdapter = new MealAdapter(context, meal);
	//holder.rv_meals.setAdapter(mealAdapter);

}

@Override
public int getItemCount() {
	return (days != null) ? days.size() : 0;
}

public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView dayTextView, monthTextView;
		
	
	
	public ViewHolder(@NonNull View itemView) {
		super(itemView);
		dayTextView = itemView.findViewById(R.id.dayTextView);
		monthTextView = itemView.findViewById(R.id.monthTextView);
	
	}
}
}
