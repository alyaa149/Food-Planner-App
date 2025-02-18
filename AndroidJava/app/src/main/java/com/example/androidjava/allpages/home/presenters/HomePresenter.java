package com.example.androidjava.allpages.home.presenters;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface HomePresenter {
	void getDailyInspration();
	void showCategories();
	void showCountries();
	void showIngredients();
void addMealToFavorites(Meal meal);
void removeMealFromFavorites(Meal meal);
void deleteMealFireBase(Meal meal);
void addMealToFireBase(Meal meal);

void insertAllFavMeals(List<Meal> meals);
void insertAllPlannedMeals(List<Meal> plannedMeals);
void getAllFavMeals(List<Meal> meals);
void getAllPlannedMeals(List<Meal> plannedMeals);



}
