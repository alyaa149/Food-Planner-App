package com.example.androidjava.allpages.home.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Repository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.allpages.home.views.HomeView;
import com.example.androidjava.network.NetworkCallback;

public class HomePresenterImpl implements HomePresenter, NetworkCallback {
private HomeView homeView;
private Repository repository;

public HomePresenterImpl(HomeView homeView, Repository repository) {
	this.homeView = homeView;
	this.repository = repository;
}

@Override
public void getDailyInspration() {
	repository.getRandomMeal(this);
	
}

@Override
public void showCategories() {
	repository.getAllCategories(this);
}

@Override
public void showCountries() {
	repository.getAllCountries(this);
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
