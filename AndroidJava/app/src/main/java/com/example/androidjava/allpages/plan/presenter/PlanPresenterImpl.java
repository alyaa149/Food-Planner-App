package com.example.androidjava.allpages.plan.presenter;

import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.allpages.plan.view.PlansView;

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
	mealsRepository.getAllPlannedMeals()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
					meals -> planView.showMeals(meals),
					error -> planView.showError(error.getMessage())
			)
	
	;
	
}

@Override
public void removeMealFromPlanned(PlannedMeal meal) {
	mealsRepository.deletePlannedMeal(meal);
}




}
