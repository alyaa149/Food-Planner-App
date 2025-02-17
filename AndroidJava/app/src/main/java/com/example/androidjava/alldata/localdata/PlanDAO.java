package com.example.androidjava.alldata.localdata;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlanDAO {
@Insert(onConflict = OnConflictStrategy.REPLACE)
Completable insertPlannedMeal(PlannedMeal meal);

@Delete
Completable deletePlannedMeal(PlannedMeal meal);

@Query("SELECT * FROM planned_meals Where userIdPlan = :userId")
Observable<List<PlannedMeal>> getAllPlannedMeals(String userId);

@Query("SELECT * FROM planned_meals WHERE userIdPlan =:userId AND day = :day AND month = :month AND year = :year")
Observable<List<PlannedMeal>> getPlannedMealByDate(String userId ,int day, int month, int year);

@Query("DELETE FROM planned_meals WHERE userIdPlan=:userId AND mealId = :mealId AND day = :day AND month = :month AND year = :year")
Completable deletePlannedMealsByDate(String userId,String mealId,int day,int month,int year);

@Insert(onConflict = OnConflictStrategy.REPLACE)
Completable insertPlannedMeals(List<PlannedMeal> meals);
}
