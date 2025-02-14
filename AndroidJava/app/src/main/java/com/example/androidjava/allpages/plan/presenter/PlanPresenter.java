package com.example.androidjava.allpages.plan.presenter;

import com.example.androidjava.Models.PlannedMeal;

public interface PlanPresenter {
	void getPlannedMeals();
	void removeMealFromPlanned(PlannedMeal meal);

}
