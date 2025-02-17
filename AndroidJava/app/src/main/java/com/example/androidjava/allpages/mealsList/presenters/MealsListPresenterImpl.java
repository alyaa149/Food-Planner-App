package com.example.androidjava.allpages.mealsList.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.allpages.mealsList.views.MealsListView;
import com.example.androidjava.network.NetworkCallback;
import com.example.androidjava.network.RealTimeFireBaseCallBack;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MealsListPresenterImpl implements MealsListPresenter , NetworkCallback {
private MealsListView mealsListView;
private RepositoryImpl mealsRepository;
private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
public void addMealToFavorites(Meal meal) {
	meal.setUserId(userId);
	mealsRepository.addToFavorites(meal);
	
}

@Override
public void removeMealFromFavorites(Meal meal) {
	meal.setUserId(userId);
	mealsRepository.delete(meal);
}

@Override
public void updateMeals(List<Meal> meals) {

}

@Override
public void addToFavFireBase(Meal meal) {
	mealsRepository.insertDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			mealsListView.showSuccess("successfully addedd fireBase");
		}
		
		@Override
		public void onFailure(Exception e) {
			mealsListView.showError("failed to add to fireBase");
		}
		
		@Override
		public void onSuccess(List<PlannedMeal> meals) {
		
		}
	});
	
}

@Override
public void removeFromFavFireBase(Meal meal) {
	mealsRepository.deleteDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			mealsListView.showSuccess("successfully removed from fireBase");
		}
		
		@Override
		public void onFailure(Exception e) {
			mealsListView.showError("failed to remove from fireBase");
		}
		
		@Override
		public void onSuccess(List<PlannedMeal> meals) {
		
		}
	});
	
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
mealsListView.showError(errorMessage);
}
}
