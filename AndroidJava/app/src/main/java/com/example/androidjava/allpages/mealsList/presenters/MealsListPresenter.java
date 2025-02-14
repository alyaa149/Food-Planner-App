package com.example.androidjava.allpages.mealsList.presenters;

import com.example.androidjava.Models.Meal;

public interface MealsListPresenter {
 void getMealsByCategory(String category);
void  getMealsByCountry(String  country);
void addMealToFavorites(Meal meal);
void removeMealFromFavorites(Meal meal);

}
