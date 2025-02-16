package com.example.androidjava.allpages.plan.presenter;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

public interface PlanPresenter {
	void getPlannedMeals();
	void getMealsByDay(int day ,int month ,int year);
void addMealToFavorites(Meal meal);
	void removeMealFromPlanned(String userId, String mealId, int day, int month, int year);

}
