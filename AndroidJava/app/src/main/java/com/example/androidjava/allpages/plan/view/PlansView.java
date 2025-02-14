package com.example.androidjava.allpages.plan.view;

import com.example.androidjava.Models.PlannedMeal;

import java.util.List;

public interface PlansView {
	void showMeals(List<PlannedMeal> meals);
	void showError(String message);
//	void showMeal(PlannedMeal meal);
}
