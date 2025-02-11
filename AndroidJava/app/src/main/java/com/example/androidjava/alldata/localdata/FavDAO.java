package com.example.androidjava.alldata.localdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidjava.Models.FavMeal;

import java.util.List;
@Dao
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
