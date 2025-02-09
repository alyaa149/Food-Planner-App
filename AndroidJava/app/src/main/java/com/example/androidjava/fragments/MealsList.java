package com.example.androidjava.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
import com.example.androidjava.adapters.MealAdapter;
import com.example.androidjava.listeners.OnMealClickListener;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MealsList extends Fragment implements OnMealClickListener {
RecyclerView recyclerView;
MealAdapter mealAdapter;
List<Meal> mealList =new ArrayList<>();


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

View view;
private String mParam1;
private String mParam2;
String country ,category;
public MealsList() {

}


public static MealsList newInstance(String param1, String param2) {
	MealsList fragment = new MealsList();
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
	view = inflater.inflate(R.layout.fragment_meals_list, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
	if (getArguments() == null) {
		Toast.makeText(getContext(), "Arguments are null", Toast.LENGTH_SHORT).show();
	} else {
		category = getArguments().getString("category");
		country = getArguments().getString("country");
		
		Toast.makeText(getContext(), "Selected Category: " + category, Toast.LENGTH_SHORT).show();
		Toast.makeText(getContext(), "Selected Country: " + country, Toast.LENGTH_SHORT).show();
		
		
		recyclerView = view.findViewById(R.id.recyclerView);
		mealAdapter = new MealAdapter(getContext(), mealList, MealsList.this);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(mealAdapter);
		
		
		if (category != null ) {
			//Toast.makeText(getContext(), "Category innside !=null= " + category+ " ,Country = "+country, Toast.LENGTH_SHORT).show();
			getMealsByCategory(category);
		} else if (country != null ) {
		//	Toast.makeText(getContext(), "country innside ==null: " + category, Toast.LENGTH_SHORT).show();
			
			getMealsByCountry(country);
		}
	}
}
private void getMealsByCountry(String country) {
	MealsRemoteDataSourceImpl.searchByArea(country, new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				
				Toast.makeText(getContext(), "API call successful!", Toast.LENGTH_SHORT).show();
				
				mealList.clear();
				mealList.addAll(response.body().getMeals());
				mealAdapter.notifyDataSetChanged();
			} else {
				
				Toast.makeText(getContext(), "Response not successful or body is null", Toast.LENGTH_SHORT).show();
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Toast.makeText(getContext(), "Failed to fetch meals: " + t.getMessage(), Toast.LENGTH_SHORT).show();
		}
	});
}


private void getMealsByCategory(String category) {
	MealsRemoteDataSourceImpl.searchByCategory(category, new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				
				//Toast.makeText(getContext(), "API call successful!", Toast.LENGTH_SHORT).show();
				
				mealList.clear();
				mealList.addAll(response.body().getMeals());
				mealAdapter.notifyDataSetChanged();
			} else {
			
				Toast.makeText(getContext(), "Response not successful or body is null", Toast.LENGTH_SHORT).show();
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Toast.makeText(getContext(), "Failed to fetch meals: " + t.getMessage(), Toast.LENGTH_SHORT).show();
		}
	});
}



@Override
public void onMealClick(Meal meal) {
	Bundle bundle = new Bundle();
	if (meal == null || meal.getIdMeal() == null) {
		Toast.makeText(getContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
		return;
	}
	
	bundle.putString("name", meal.getIdMeal());
	Navigation.findNavController(view).navigate(R.id.action_mealsList_to_mealDetails, bundle);
	
	
}
}