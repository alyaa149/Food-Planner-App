package com.example.androidjava.allpages.plan.presenter;

import android.util.Log;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.allpages.plan.view.PlansView;
import com.example.androidjava.network.AllMealsCallBackFirBase;
import com.example.androidjava.network.RealTimeFireBaseCallBack;
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
public void getPlannedMealsByDate(String userId, int day, int month, int year) {
	mealsRepository.getPlannedMealsByDate(userId, day, month, year, new AllMealsCallBackFirBase<List<PlannedMeal>>() {
		@Override
		public void onSuccess(List<PlannedMeal> plannedMeals) {
			List<Meal> meals = new ArrayList<>();
			
			for (PlannedMeal plannedMeal : plannedMeals) {
				if (plannedMeal != null && plannedMeal.getMeal() != null) {
					meals.add(plannedMeal.getMeal());
				} else {
					Log.e("DEBUG", "Skipping a null PlannedMeal or Meal object");
				}
			}
			
			if (meals.isEmpty()) {
				Log.d("DEBUG", "No planned meals found in Firebase.");
				planView.showError("No meals found for this date.");
			} else {
				Log.d("DEBUG", "Meals in presenter in Firebase: " + meals.size());
				Log.d("DEBUG", "First Meal in presenter in Firebase: " + (meals.get(0) != null ? meals.get(0).getStrMeal() : "Null Meal"));
				planView.showMealsByFireBase(meals);
			}
		}
		
		@Override
		public void onFailure(String error) {
			Log.e("DEBUG", "Error fetching planned meals in Firebase: " + error);
			planView.showError(error);
		}
	});
}

@Override
public void getMealsByDay(int day, int month, int year) {
	mealsRepository.getPlannedMealByDate(FirebaseAuth.getInstance().getCurrentUser().getUid(), day, month, year)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.map(plannedMeals -> {
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
public void removeMealFromPlanned( String mealId, int day, int month, int year) {
	mealsRepository.deletePlannedMeal(FirebaseAuth.getInstance().getCurrentUser().getUid(), mealId, day, month, year);
}

@Override
public void addMealToFavFireBase(Meal meal) {
	mealsRepository.insertDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			planView.showSuccessFireBase("Meal added to favorites");
		}
		
		@Override
		public void onFailure(Exception e) {
			planView.showError(e.getMessage());
		}
	});
	
}

@Override
public void removeMealFromPlannedFireBase(int day, int month, int year, Meal meal) {
	mealsRepository.deleteDBUsersPlanReference(day, month, year, meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			planView.showSuccessFireBase("Meal removed from planned");
		}
		
		@Override
		public void onFailure(Exception e) {
			planView.showError(e.getMessage());
		}
	});
}


}
