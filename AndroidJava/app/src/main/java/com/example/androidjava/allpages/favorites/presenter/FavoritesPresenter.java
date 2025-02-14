package com.example.androidjava.allpages.favorites.presenter;

import com.example.androidjava.Models.Meal;

public interface FavoritesPresenter {
	void getFavorites();
	void removeMealFromFavorites(Meal meal);
}
