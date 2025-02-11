package com.example.androidjava.mealDetails.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealRepository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.mealDetails.views.MealDetailsView;
import com.example.androidjava.network.NetworkCallback;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, NetworkCallback {
private MealRepository mealRepository;
private MealDetailsView mealDetailsView;

public MealDetailsPresenterImpl(MealRepository mealRepository, MealDetailsView mealDetailsView) {
	this.mealRepository = mealRepository;
	this.mealDetailsView = mealDetailsView;
}

@Override
public void onSuccessMeal(MealResponse response) {
    mealDetailsView.showMealDetails(response.getMeals().get(0));
}

@Override
public void onSuccess(CategoryResponse response) {

}

@Override
public void onSuccessCountry(MealResponse response) {

}

@Override
public void onFailure(String errorMessage) {
mealDetailsView.showError(errorMessage);
}

@Override
public void getMealDetails(int mealId) {
	mealRepository.getMealById(mealId, this);
}

@Override
public void showError(String errorMessage) {
	mealDetailsView.showError("Error: " + errorMessage);
}
}

