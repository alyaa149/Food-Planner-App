package com.example.androidjava.alldata.localdata;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalDataSource {
void insert(Meal meal);
void delete(Meal meal);
Observable<List<Meal>> getAllMeals(String userId);
void insertMeals(List<Meal> meals);
void insertPlannedMeal(PlannedMeal meal);
void deletePlannedMeal(String userId, String mealId, int day, int month, int year);
Observable<List<PlannedMeal>> getAllPlannedMeals(String userId);
Observable<List<PlannedMeal>> getPlannedMealByDate(String userId ,int day, int month, int year);
void deletePlannedMealsByDate(String date);
}
