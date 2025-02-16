package com.example.androidjava.allpages.mealDetails.presenters;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.Repository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.allpages.mealDetails.views.MealDetailsView;
import com.example.androidjava.network.NetworkCallback;
import com.example.androidjava.network.RealTimeFireBaseCallBack;
import com.google.firebase.auth.FirebaseAuth;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, NetworkCallback {
private Repository repository;
private MealDetailsView mealDetailsView;

public MealDetailsPresenterImpl(Repository repository, MealDetailsView mealDetailsView) {
	this.repository = repository;
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
	repository.getMealById(mealId, this);
}

@Override
public void showError(String errorMessage) {
	mealDetailsView.showError("Error: " + errorMessage);
}

@Override
public void insertPlannedMeal(Meal meal, int day ,int month, int year) {
	String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
	PlannedMeal plannedMeal = new PlannedMeal(meal,Integer.parseInt(meal.getIdMeal()) ,userId,day,month,year);
	repository.insertPlannedMeal(plannedMeal);
	
}

@Override
public void insertPlannedMealFireBase(int day ,int month, int year, Meal meal) {
	repository.insertDBUsersPlanReference(day,month,year,meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			mealDetailsView.showSuccess("Meal inserted successfully into Fire Base");
		}
		
		@Override
		public void onFailure(Exception e) {
			mealDetailsView.showError(
					"Failed to insert meal into fire Base: " + e.getMessage()
			);
		}
	});
	
	
	
}


}

