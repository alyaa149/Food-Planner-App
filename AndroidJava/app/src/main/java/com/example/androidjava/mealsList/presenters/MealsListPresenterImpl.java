package com.example.androidjava.mealsList.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealRepositoryImpl;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.mealsList.views.MealsListView;
import com.example.androidjava.network.NetworkCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsListPresenterImpl implements MealsListPresenter , NetworkCallback {
private MealsListView mealsListView;
private MealRepositoryImpl mealsRepository;

public MealsListPresenterImpl(MealsListView mealsListView, MealRepositoryImpl mealsRepository) {
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
