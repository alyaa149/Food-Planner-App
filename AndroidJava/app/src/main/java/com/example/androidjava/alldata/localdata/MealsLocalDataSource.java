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
void insertPlannedMeal(PlannedMeal meal);
void deletePlannedMeal(PlannedMeal meal);
Observable<List<PlannedMeal>> getAllPlannedMeals();
void deletePlannedMealsByDate(String date);
}
