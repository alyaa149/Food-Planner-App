//package com.example.androidjava.db.localdata;
//
//import android.content.Context;
//
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.example.androidjava.Models.Meal;
//
//@androidx.room.Database(entities = {Meal.class}, version = 1)
//public abstract class AppDataBase extends RoomDatabase {
//private static AppDataBase instanse = null;
//
//public abstract FavDAO getMealDao();
//
//public static synchronized AppDataBase getInstanse(Context context) {
//	if (instanse == null) {
//		instanse = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "mealsdb").build();
//
//	}
//	return instanse;
//
//}
//
//}