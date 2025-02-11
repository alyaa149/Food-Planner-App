package com.example.androidjava.Models;

import com.example.androidjava.network.NetworkCallback;

public interface MealRepository {
public void getAllCategories(NetworkCallback callback);
public void getAllCountries(NetworkCallback callback) ;
public void getRandomMeal(NetworkCallback callback);
public void getMealsByCategory(String category, NetworkCallback callback);
public void getMealsByCountry(String country, NetworkCallback callback);
public void getMealById(int mealId, NetworkCallback callback);


}
