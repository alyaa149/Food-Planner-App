package com.example.androidjava.allpages.home.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.Repository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.allpages.home.views.HomeView;
import com.example.androidjava.network.NetworkCallback;
import com.example.androidjava.network.RealTimeFireBaseCallBack;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, NetworkCallback {
private HomeView homeView;
private Repository repository;
private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
public void addMealToFavorites(Meal meal) {
	meal.setUserId(userId);
	repository.addToFavorites(meal);
	
}

@Override
public void removeMealFromFavorites(Meal meal) {
	meal.setUserId(userId);
	repository.delete(meal);
}

@Override
public void deleteMealFireBase(Meal meal) {
	repository.deleteDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			homeView.showFireBaseSuccess("Meal deleted successfully!");
		}
		
		@Override
		public void onFailure(Exception e) {
			homeView.showError("Error: " + e.getMessage());
		}
		
		@Override
		public void onSuccess(List<PlannedMeal> meals) {
		
		}
	});
	
}

@Override
public void addMealToFireBase(Meal meal) {
	repository.insertDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			homeView.showFireBaseSuccess("Meal added successfully!");
		}
		
		@Override
		public void onFailure(Exception e) {
			homeView.showError("Error: " + e.getMessage());
		}
		
		@Override
		public void onSuccess(List<PlannedMeal> meals) {
		
		}
	});
	
	
}


@Override
public void insertAllFavMeals(List<Meal> meals) {

}

@Override
public void insertAllPlannedMeals(List<Meal> plannedMeals) {

}

@Override
public void getAllFavMeals(List<Meal> meals) {

}

@Override
public void getAllPlannedMeals(List<Meal> plannedMeals) {
	//repository.(plannedMeals);
	
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
