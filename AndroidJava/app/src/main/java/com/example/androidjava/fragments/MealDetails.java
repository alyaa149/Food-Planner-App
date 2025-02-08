package com.example.androidjava.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
import com.example.androidjava.adapters.IngrediantAdapter;
import com.example.androidjava.listeners.OnIngredientClickListener;
import com.example.androidjava.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetails extends Fragment implements OnIngredientClickListener {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";


private String mParam1;
private String mParam2;
RecyclerView recyclerView;
IngrediantAdapter ingrediantAdapter;
Meal meal;

public MealDetails() {

}


public static MealDetails newInstance(String param1, String param2) {
	MealDetails fragment = new MealDetails();
	Bundle args = new Bundle();
	args.putString(ARG_PARAM1, param1);
	args.putString(ARG_PARAM2, param2);
	fragment.setArguments(args);
	return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
		mParam1 = getArguments().getString(ARG_PARAM1);
		mParam2 = getArguments().getString(ARG_PARAM2);
	}
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	
	return inflater.inflate(R.layout.fragment_meal_details, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	Meal meal = getTheMealFromArguments();
	Toast.makeText(getContext(), "Meal ingredient0 : " + meal.getStrIngredient1(), Toast.LENGTH_SHORT).show();
	getMealDetails();
	
	recyclerView = view.findViewById(R.id.recyclerView);
	//ingrediantAdapter = new IngrediantAdapter(getContext(), ,MealDetails.this);
//	recyclerView.setAdapter(ingrediantAdapter);
	
}
private void getMealDetails(){



}

private Meal getTheMealFromArguments() {
	Bundle bundle = getArguments();
	if (bundle != null) {
		meal = (Meal) bundle.getSerializable("mealObject");
		
	}else {
		Toast.makeText(getContext(), "Bundle is null", Toast.LENGTH_SHORT).show();
	}
	Toast.makeText(getContext(), "Meal Name form bundle : " + meal.getStrMeal(), Toast.LENGTH_SHORT).show();
	return meal;
}

@Override
public void onIngredientClick(String Ingredient) {

}

}