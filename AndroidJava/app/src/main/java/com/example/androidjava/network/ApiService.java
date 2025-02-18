package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

@GET("random.php")
Single<MealResponse> getRandomMeal();
@GET("lookup.php")
Single<MealResponse> getMealById(@Query("i") int mealId);
@GET("list.php?a=list")
Single<MealResponse> getAllAreas();
@GET("list.php?i=list")
Single<MealResponse> getAllIngredients();
@GET("categories.php")
Single<CategoryResponse> getAllCategories();
@GET("filter.php")
Single<MealResponse> filterByIngredient(@Query("i") String ingredient);
@GET("filter.php")
Single<MealResponse> filterByCategory(@Query("c") String Category);

@GET("filter.php")
Single<MealResponse> filterByArea(@Query("a") String Area);
@GET("search.php")
Single<MealResponse> searchByName(@Query("s") String name);

}
