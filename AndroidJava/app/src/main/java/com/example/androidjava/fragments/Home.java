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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
import com.example.androidjava.adapters.CategoryAdapter;
import com.example.androidjava.adapters.MealAdapter;
import com.example.androidjava.network.ApiClient;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
private List<Category> categoryList = new ArrayList<>();
private List<Meal> areasList = new ArrayList<>();
RecyclerView recyclerView;
MealAdapter mealAdapter;
CardView randomMealCard;
Chip categotyChip;
Chip countryChip;

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
	categotyChip =view.findViewById(R.id.categoryChip);
	countryChip =view.findViewById(R.id.countryChip);
	recyclerView =view.findViewById(R.id.recyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
	randomMealCard = view.findViewById(R.id.includedMealCell);
	countryChip.setOnClickListener(v ->showCountries());
	categotyChip.setOnClickListener(v ->showCategories());
	getDailyInspration();
	super.onViewCreated(view, savedInstanceState);
}
public void showCountries(){
	ApiClient.getAllCountries(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if(response.isSuccessful() && response.body() != null){
				areasList =response.body().getMeals();
				Toast.makeText(getContext(), "Country Name: " +areasList.get(0).getStrArea(), Toast.LENGTH_SHORT).show();
				MealAdapter mealAdapter = new MealAdapter(getContext(), areasList);
				recyclerView.setAdapter(mealAdapter);
				mealAdapter.notifyDataSetChanged();
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Toast.makeText(getContext(), "Failed to fetch areas: " + t.getMessage(), Toast.LENGTH_SHORT).show();
			
		}
	});
}

public void showCategories()
{
	ApiClient.getAllCategories(new Callback<CategoryResponse>() {
		@Override
		public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				categoryList = response.body().getCategories();
				CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
					recyclerView.setAdapter(categoryAdapter);
				categoryAdapter.notifyDataSetChanged();
			}
			Toast.makeText(getContext(), "Categoty Name: " +categoryList.get(0).getStrCategoryThumb(), Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onFailure(Call<CategoryResponse> call, Throwable t) {
		Toast.makeText(getContext(), "Failed to fetch categories: " + t.getMessage(), Toast.LENGTH_SHORT).show();
		}
	});
}
public void  getDailyInspration(){
	ImageView mealImage = randomMealCard.findViewById(R.id.itemImg);
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
}

}