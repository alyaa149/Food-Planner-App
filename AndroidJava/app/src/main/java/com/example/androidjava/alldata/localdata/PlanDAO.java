package com.example.androidjava.alldata.localdata;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidjava.Models.PlannedMeal;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlanDAO {
@Insert
Completable insertPlannedMeal(PlannedMeal meal);

@Delete
Completable deletePlannedMeal(PlannedMeal meal);

@Query("SELECT * FROM planned_meals")
Observable<List<PlannedMeal>> getAllPlannedMeals();

@Query("SELECT * FROM planned_meals WHERE date = :date")
Observable<List<PlannedMeal>> getPlannedMealsByDate(String date);

@Query("DELETE FROM planned_meals WHERE date = :date")
Completable deletePlannedMealsByDate(String date);
}
