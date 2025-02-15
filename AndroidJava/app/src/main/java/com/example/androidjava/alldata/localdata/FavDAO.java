package com.example.androidjava.alldata.localdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidjava.Models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface FavDAO {
@Insert
Completable insertFavoriteMeal(Meal meal);

@Delete
Completable deleteFavoriteMeal(Meal meal);

@Query("SELECT * FROM favorite_meals WHERE userId = :userId")
Observable<List<Meal>> getAllFavoriteMeals(String userId);

@Query("SELECT * FROM favorite_meals WHERE idMeal = :mealId")
Meal checkFavoriteMealById(int mealId);
}
