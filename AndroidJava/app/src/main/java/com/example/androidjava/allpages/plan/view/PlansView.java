package com.example.androidjava.allpages.plan.view;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

import java.util.List;

public interface PlansView {
	void showAllMealsFromRoom(List<PlannedMeal> meals);
	void showMealsByDate(List<Meal> meals);
	void showError(String message);
	void showSuccessFireBase(String message);
	void showMealsByFireBase(List<Meal> meals);
	void showPlannedMeals(List<PlannedMeal> meals);
//	void showMeal(PlannedMeal meal);
}
