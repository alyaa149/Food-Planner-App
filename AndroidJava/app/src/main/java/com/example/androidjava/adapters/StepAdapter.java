//package com.example.androidjava.adapters;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//
//import com.example.androidjava.R;
//
//import java.util.List;
//
//public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
//private Context context;
//private List<String> steps;
//
//
//public StepAdapter(Context context, List<String> steps) {
//	this.context = context;
//	this.steps = steps;
//
//}
//
//
//@NonNull
//@Override
//public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//	View view = LayoutInflater.from(context).inflate(R.layout.step_item, parent, false);
//	return new StepAdapter.ViewHolder(view);
//}
//
//@Override
//public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
//	String step = steps.get(position);
//	holder.step.setText(step);
//}
//
//
//
//
//
//
//@Override
//public int getItemCount() {
//	return (steps != null) ? steps.size() : 0;
//}
//public static class ViewHolder extends RecyclerView.ViewHolder{
//
//	public TextView step;
//	public ViewHolder(@NonNull View itemView) {
//		super(itemView);
//
//		step = itemView.findViewById(R.id.stepTxt);
//
//	}
//}
//
//}