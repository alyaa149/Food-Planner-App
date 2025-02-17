package com.example.androidjava.allpages.plan.presenter;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

import java.util.List;

public interface PlanPresenter {
	void getPlannedMeals();
	void getMealsByDay(int day ,int month ,int year);
void addMealToFavorites(Meal meal);
	void removeMealFromPlanned( String mealId, int day, int month, int year);
	void addMealToFavFireBase(Meal meal);
	void removeMealFromPlannedFireBase(int day, int month, int year, Meal meal);
 void getPlannedMealsByDate(String userId, int day, int month, int year);
 void addMealsToRoom(List<PlannedMeal> meals);
void fetchPlannedMeals();

}
