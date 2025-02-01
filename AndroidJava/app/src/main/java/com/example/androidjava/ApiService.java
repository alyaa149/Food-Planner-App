package com.example.androidjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
@GET("search.php")
Call<MealResponse> searchMealByName(@Query("s") String mealName);

//@GET("lookup.php")
//Call<MealResponse> getMealById(@Query("i") String mealId);

@GET("random.php")
Call<MealResponse> getRandomMeal();



@GET("lookup.php")
Call<MealResponse> getMealById(@Query("i") int mealId);
@GET("list.php?a=list")
Call<CountryResponse> getAllAreas();


@GET("list.php?c=list")
Call<CategoryResponse> getAllCategories();
@GET("filter.php")
Call<MealResponse> filterByIngredient(@Query("i") String ingredient);


}
