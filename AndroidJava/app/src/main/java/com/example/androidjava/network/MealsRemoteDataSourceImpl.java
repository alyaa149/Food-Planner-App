package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsRemoteDataSourceImpl {
private static ApiService apiService;
private static Retrofit retrofit;
private static final String url = "https://www.themealdb.com/api/json/v1/1/";

private static MealsRemoteDataSourceImpl instance;

private MealsRemoteDataSourceImpl() {
	retrofit = new Retrofit.Builder()
			           .baseUrl(url)
			           .addConverterFactory(GsonConverterFactory.create())
			           .build();
	apiService = retrofit.create(ApiService.class);
}

public static synchronized MealsRemoteDataSourceImpl getInstance() {
	if (instance == null) {
		instance = new MealsRemoteDataSourceImpl();
	}
	return instance;
}

public static void getRandomMeal(Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.getRandomMeal();
	call.enqueue(callback);
}

public static void getAllCountries(Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.getAllAreas();
	call.enqueue(callback);
}

public static void getAllCategories(Callback<CategoryResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<CategoryResponse> call = apiService.getAllCategories();
	call.enqueue(callback);
}

public static void getMealById(int id, Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.getMealById(id);
	call.enqueue(callback);
}

public static void searchByName(String name, Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.searchByName(name);
	call.enqueue(callback);
	
}

public static void searchByIngredient(String ingredient, Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.filterByIngredient(ingredient);
	call.enqueue(callback);
	
}

public static void searchByCategory(String category, Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call = apiService.filterByCategory(category);
	call.enqueue(callback);
}

public static void searchByArea(String area, Callback<MealResponse> callback) {
	if (apiService == null) {
		getInstance();
	}
	Call<MealResponse> call =apiService.filterByArea(area);
	call.enqueue(callback);

}



}
