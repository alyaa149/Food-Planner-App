package com.example.androidjava.allpages.favorites.view;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface FavoritesView {
	void showFavProducts(List<Meal> meals);
	void showError(String message);

}
