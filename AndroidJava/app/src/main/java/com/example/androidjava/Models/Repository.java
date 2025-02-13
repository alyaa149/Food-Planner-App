package com.example.androidjava.Models;

import com.example.androidjava.network.AuthCallback;
import com.example.androidjava.network.NetworkCallback;

public interface Repository {
public void getAllCategories(NetworkCallback callback);
public void getAllCountries(NetworkCallback callback) ;
public void getRandomMeal(NetworkCallback callback);
public void getMealsByIngredient(String ingredient, NetworkCallback callback);
public void getMealsByName(String name, NetworkCallback callback);
public void getMealsByCategory(String category, NetworkCallback callback);
public void getMealsByCountry(String country, NetworkCallback callback);
public void getMealById(int mealId, NetworkCallback callback);
public void signUp(String email, String password, AuthCallback callback);
public void signIn(String email, String password, AuthCallback callback);
public void signInWithGoogle(String token, AuthCallback callback);
public void signOut(AuthCallback callback);


}
