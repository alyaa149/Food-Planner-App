package com.example.androidjava;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

@GET("random.php")
Call<MealResponse> getRandomMeal();
@GET("lookup.php")
Call<MealResponse> getMealById(@Query("i") int mealId);
@GET("list.php?a=list")
Call<MealResponse> getAllAreas();
@GET("categories.php")
Call<CategoryResponse> getAllCategories();
@GET("filter.php")
Call<MealResponse> filterByIngredient(@Query("i") String ingredient);
@GET("filter.php")
Call<MealResponse> filterByCategory(@Query("c") String Category);

@GET("filter.php")
Call<MealResponse> filterByArea(@Query("a") String Area);
@GET("search.php")
Call<MealResponse> searchByName(@Query("s") String name);

}
