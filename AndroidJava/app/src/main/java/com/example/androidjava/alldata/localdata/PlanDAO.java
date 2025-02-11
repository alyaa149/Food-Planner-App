package com.example.androidjava.alldata.localdata;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidjava.Models.PlannedMeal;

import java.util.Date;
import java.util.List;

public interface PlanDAO {
@Insert
void insertPlannedMeal(PlannedMeal meal);

@Delete
void deletePlannedMeal(PlannedMeal meal);

@Query("SELECT * FROM planned_meals")
List<PlannedMeal> getAllPlannedMeals();

@Query("SELECT * FROM planned_meals WHERE date = :date")
List<PlannedMeal> getPlannedMealsByDate(Date date);

@Query("DELETE FROM planned_meals WHERE date = :date")
void deletePlannedMealsByDate(Date date);
}
