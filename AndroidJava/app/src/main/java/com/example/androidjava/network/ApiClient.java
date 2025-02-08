package com.example.androidjava.network;

import android.util.Log;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
private static ApiService apiService;
private static Retrofit retrofit;
private static final String url = "https://www.themealdb.com/api/json/v1/1/";

private static ApiClient instance;

private ApiClient() {
	retrofit = new Retrofit.Builder()
			           .baseUrl(url)
			           .addConverterFactory(GsonConverterFactory.create())
			           .build();
	apiService = retrofit.create(ApiService.class);
}

public static synchronized ApiClient getInstance() {
	if (instance == null) {
		instance = new ApiClient();
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
