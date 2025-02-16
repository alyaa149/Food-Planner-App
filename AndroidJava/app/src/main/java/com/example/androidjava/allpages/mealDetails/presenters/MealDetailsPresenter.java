package com.example.androidjava.allpages.mealDetails.presenters;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

public interface MealDetailsPresenter  {
	void getMealDetails(int mealId);
	
    void showError(String errorMessage);
	void  insertPlannedMeal(Meal meal, int day ,int month, int year);
	void insertPlannedMealFireBase(int day,int month,int year,Meal meal);
}
