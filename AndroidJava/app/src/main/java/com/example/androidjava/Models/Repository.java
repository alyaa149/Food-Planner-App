package com.example.androidjava.Models;

import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthCallback;
import com.example.androidjava.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

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
//====================================================
//fav meals
void addToFavorites(Meal meal);
Observable<List<Meal>> getAllFavorites(String userId);
void delete(Meal meal);
//=======================================================
//planned meals
void insertPlannedMeal(PlannedMeal meal);
void deletePlannedMeal(PlannedMeal meal);
Observable<List<PlannedMeal>> getAllPlannedMeals(String userId);
Observable<List<PlannedMeal>> getPlannedMealByDate(String userId ,int day, int month, int year);
void deletePlannedMealsByDate(String date);



}
