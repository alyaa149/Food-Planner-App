package com.example.androidjava.allpages.home.presenters;

import com.example.androidjava.Models.Meal;

public interface HomePresenter {
	void getDailyInspration();
	void showCategories();
	void showCountries();
void addMealToFavorites(Meal meal);
void removeMealFromFavorites(Meal meal);
void deleteMealFireBase(Meal meal);
void addMealToFireBase(Meal meal);

}
