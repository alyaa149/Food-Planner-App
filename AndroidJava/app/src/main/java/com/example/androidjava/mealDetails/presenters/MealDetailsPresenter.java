package com.example.androidjava.mealDetails.presenters;

public interface MealDetailsPresenter  {
	void getMealDetails(int mealId);
	
    void showError(String errorMessage);
}
