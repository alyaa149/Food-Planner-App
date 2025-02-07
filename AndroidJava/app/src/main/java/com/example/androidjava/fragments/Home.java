package com.example.androidjava.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
import com.example.androidjava.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
RecyclerView recyclerView;

private String mParam1;
private String mParam2;

public Home() {

}


public static Home newInstance(String param1, String param2) {
	Home fragment = new Home();
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

	return inflater.inflate(R.layout.fragment_home, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	recyclerView =view.findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
	CardView randomMealCard = view.findViewById(R.id.includedMealCell);
	ImageView mealImage = randomMealCard.findViewById(R.id.foodImg);
	TextView mealName = randomMealCard.findViewById(R.id.Title);
	TextView  mealDesc = randomMealCard.findViewById(R.id.desc);
	
	ApiClient.getRandomMeal(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				Meal randomMeal = response.body().getMeals().get(0);
				
				//Toast.makeText(getContext(), "Meal Img: " + randomMeal.getStrMealThumb(), Toast.LENGTH_SHORT).show();
				Glide.with(getContext())
						.load(randomMeal.getStrMealThumb())
						.into(mealImage);
				mealName.setText(randomMeal.getStrMeal());
					mealDesc.setText("."+randomMeal.getStrArea());
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.e("API_ERROR", "Failed to fetch meal: " + t.getMessage());
		}
	});



	super.onViewCreated(view, savedInstanceState);
}

}