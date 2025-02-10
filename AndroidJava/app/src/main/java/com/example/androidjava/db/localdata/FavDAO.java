package com.example.androidjava.db.localdata;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidjava.Models.FavMeal;

import java.util.List;

public interface FavDAO {
@Insert
void insertFavoriteMeal(FavMeal meal);

@Delete
void deleteFavoriteMeal(FavMeal meal);

@Query("SELECT * FROM favorite_meals")
List<FavMeal> getAllFavoriteMeals();

@Query("SELECT * FROM favorite_meals WHERE idMeal = :mealId")
FavMeal getFavoriteMealById(String mealId);
}
