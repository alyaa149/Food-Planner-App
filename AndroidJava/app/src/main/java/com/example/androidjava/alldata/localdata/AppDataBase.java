package com.example.androidjava.alldata.localdata;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

@androidx.room.Database(entities = {Meal.class, PlannedMeal.class}, version = 7)
public abstract class AppDataBase extends RoomDatabase {
private static AppDataBase instanse = null;
public abstract PlanDAO getPlanMealDao();
public abstract FavDAO getMealDao();

public static synchronized AppDataBase getInstanse(Context context) {
	if (instanse == null) {
		instanse = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "mealsdb").fallbackToDestructiveMigration().build();

	}
	return instanse;

}

}