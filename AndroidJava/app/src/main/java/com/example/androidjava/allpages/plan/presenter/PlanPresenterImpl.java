package com.example.androidjava.allpages.plan.presenter;

import android.util.Log;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.allpages.plan.view.PlansView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {

 PlansView planView;
 RepositoryImpl mealsRepository;
public PlanPresenterImpl (PlansView planView,RepositoryImpl mealsRepository){
	this.planView=planView;
	this.mealsRepository=mealsRepository;
	
}
@Override
public void getPlannedMeals() {
	mealsRepository.getAllPlannedMeals(FirebaseAuth.getInstance().getCurrentUser().getUid())
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
					meals -> {
						Log.d("DEBUG", "Meals in presenter: " + meals.size());
						planView.showMeals(meals);},
					error -> planView.showError(error.getMessage())
			);
}

@Override
public void getMealsByDay(int day, int month, int year) {
	mealsRepository.getPlannedMealByDate(FirebaseAuth.getInstance().getCurrentUser().getUid(), day, month, year)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.map(plannedMeals -> { // Convert PlannedMeal -> Meal
				List<Meal> meals = new ArrayList<>();
				for (PlannedMeal plannedMeal : plannedMeals) {
					meals.add(plannedMeal.getMeal());
				}
				return meals;
			})
			.subscribe(
					meals -> {
						Log.d("DEBUG", "Meals in presenter: " + meals.size());
						planView.showMealsByDate(meals); // Return only meals
					},
					error -> planView.showError(error.getMessage())
			);
}

@Override
public void addMealToFavorites(Meal meal) {
	meal.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
	mealsRepository.addToFavorites(meal);
	
}


@Override
public void removeMealFromPlanned(String userId, String mealId, int day, int month, int year) {
	mealsRepository.deletePlannedMeal(userId, mealId, day, month, year);
}




}
