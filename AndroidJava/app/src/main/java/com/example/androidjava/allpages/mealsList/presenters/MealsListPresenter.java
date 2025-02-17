package com.example.androidjava.allpages.mealsList.presenters;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface MealsListPresenter {
 void getMealsByCategory(String category);
void  getMealsByCountry(String  country);
void addMealToFavorites(Meal meal);
void removeMealFromFavorites(Meal meal);
void updateMeals(List<Meal> meals);
void addToFavFireBase(Meal meal);
void removeFromFavFireBase(Meal meal);

}
