package com.example.androidjava.allpages.mealsList.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.allpages.mealsList.views.MealsListView;
import com.example.androidjava.network.NetworkCallback;

public class MealsListPresenterImpl implements MealsListPresenter , NetworkCallback {
private MealsListView mealsListView;
private RepositoryImpl mealsRepository;

public MealsListPresenterImpl(MealsListView mealsListView, RepositoryImpl mealsRepository) {
	this.mealsListView = mealsListView;
	this.mealsRepository = mealsRepository;
}
@Override
public void getMealsByCategory(String category) {
	mealsRepository.getMealsByCategory(category, this);


}

@Override
public void getMealsByCountry(String country) {
mealsRepository.getMealsByCountry(country, this);
}

@Override
public void onSuccessMeal(MealResponse response) {
	mealsListView.showMeals(response.getMeals());
}

@Override
public void onSuccess(CategoryResponse response) {

}

@Override
public void onSuccessCountry(MealResponse response) {

}

@Override
public void onFailure(String errorMessage) {

}
}
