package com.example.androidjava.allpages.mealDetails.presenters;

public interface MealDetailsPresenter  {
	void getMealDetails(int mealId);
	
    void showError(String errorMessage);
}
