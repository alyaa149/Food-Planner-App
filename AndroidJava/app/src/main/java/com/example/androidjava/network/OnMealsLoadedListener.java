package com.example.androidjava.network;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface OnMealsLoadedListener {
	void onMealsLoaded(List<Meal> meals);
	void onError(String errorMessage);
}
