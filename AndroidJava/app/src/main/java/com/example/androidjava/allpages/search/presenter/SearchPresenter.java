package com.example.androidjava.allpages.search.presenter;

import com.example.androidjava.Models.Meal;

public interface SearchPresenter {
	void searchMeals(String query);
	void searchCategories(String query);
	void searchCountries(String query);
	void searchIngredients(String query);
void addMealToFavorites(Meal meal);
void removeMealFromFavorites(Meal meal);

}
