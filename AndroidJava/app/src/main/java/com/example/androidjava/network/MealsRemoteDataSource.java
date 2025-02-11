package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;

public interface MealsRemoteDataSource {

	void getCategories(NetworkCallback callback);
	void getCountries(NetworkCallback callback);
	void getRandomMeal(NetworkCallback callback);
	void getMealById(int id, NetworkCallback callback);
	void getMealByName(String name, NetworkCallback callback);
	void getMealByCategory(String category, NetworkCallback callback);
	void getMealByArea(String area, NetworkCallback callback);
	void getMealByIngredient(String ingredient, NetworkCallback callback);
	void getMealByLetter(char letter, NetworkCallback callback);
	void getMealBySearch(String query, NetworkCallback callback);


}
