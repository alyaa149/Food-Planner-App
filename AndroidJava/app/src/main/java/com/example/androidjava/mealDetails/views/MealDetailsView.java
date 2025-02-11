package com.example.androidjava.mealDetails.views;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface MealDetailsView {
	void showMealDetails(Meal meal);
void showError(String message);
}
