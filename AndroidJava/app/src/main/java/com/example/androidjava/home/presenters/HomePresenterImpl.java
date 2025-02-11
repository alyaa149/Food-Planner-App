package com.example.androidjava.home.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealRepository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.home.views.HomeView;
import com.example.androidjava.network.NetworkCallback;

public class HomePresenterImpl implements HomePresenter, NetworkCallback {
private HomeView homeView;
private MealRepository mealRepository;

public HomePresenterImpl(HomeView homeView, MealRepository mealRepository) {
	this.homeView = homeView;
	this.mealRepository = mealRepository;
}

@Override
public void getDailyInspration() {
	mealRepository.getRandomMeal(this);
	
}

@Override
public void showCategories() {
	mealRepository.getAllCategories(this);
}

@Override
public void showCountries() {
	mealRepository.getAllCountries(this);
}


@Override
public void onSuccessMeal(MealResponse response) {

homeView.showRandomMeal(response.getMeals().get(0));
}

@Override
public void onSuccess(CategoryResponse response) {
	homeView.showCategories(response.getCategories());

}

@Override
public void onSuccessCountry(MealResponse response) {
	homeView.showCountries(response.getMeals());

}

@Override
public void onFailure(String errorMessage) {
	homeView.showError(errorMessage);
	
}
}
