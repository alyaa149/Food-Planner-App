package com.example.androidjava.allpages.mealsList.views;

import com.example.androidjava.Models.Meal;

import java.util.List;

public interface MealsListView {

void showMeals(List<Meal> meals);
void showError(String message);
void showSuccess(String message);
}
