package com.example.androidjava.allpages.favorites.presenter;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface FavoritesPresenter {
	void getFavorites();
	void removeMealFromFavorites(Meal meal);
	void deleteFavMealFireBase(Meal meal);
void getFavoritesFromFireBase();
void addAllMealsFromFireBase(List<Meal> meals);

}
