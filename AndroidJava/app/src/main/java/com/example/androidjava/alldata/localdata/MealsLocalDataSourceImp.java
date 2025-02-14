package com.example.androidjava.alldata.localdata;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.alldata.localdata.PlanDAO;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSourceImp implements MealsLocalDataSource {
private PlanDAO dao;
private FavDAO favDao;
private static MealsLocalDataSourceImp localDataSource = null;
Observable<List<Meal>> storedFavMeals;
Observable<List<PlannedMeal>> storedPlannedMeals;

private MealsLocalDataSourceImp(Context context) {
	AppDataBase db = AppDataBase.getInstanse(context.getApplicationContext());
	favDao = db.getMealDao();
	storedFavMeals = favDao.getAllFavoriteMeals();
	dao = db.getPlanMealDao();
	storedPlannedMeals = dao.getAllPlannedMeals();
}

public static MealsLocalDataSourceImp getInstance(Context context) {
	if (localDataSource == null) {
		localDataSource = new MealsLocalDataSourceImp(context);
	}
	return localDataSource;
}

@Override
public void insert(Meal meal) {
	favDao.insertFavoriteMeal(meal).subscribeOn(Schedulers.io())
			.subscribe(() -> Log.d("DEBUG", "Product Inserted"),
					error -> Log.e("DEBUG", "Insert Error", error));
	
}

@Override
public void delete(Meal meal) {
	favDao.deleteFavoriteMeal(meal).subscribeOn(Schedulers.io())
			.subscribe(() -> Log.d("DEBUG", "Product Deleted"),
					error -> Log.e("DEBUG", "Delete Error", error));
	
}

@Override
public Observable<List<Meal>> getAllMeals() {
	return storedFavMeals;
}

@Override
public void insertPlannedMeal(PlannedMeal meal) {
	dao.insertPlannedMeal(meal)
			.subscribeOn(Schedulers.io())
			.subscribe(
					() -> Log.d("DEBUG", "Product Inserted"),
					error -> Log.e("DEBUG", "Insert Error", error)
			);
	
}

@Override
public void deletePlannedMeal(PlannedMeal meal) {
	dao.deletePlannedMeal(meal)
			.subscribeOn(Schedulers.io())
			.subscribe(
					() -> Log.d("DEBUG", "Product Deleted"),
					error -> Log.e("DEBUG", "Delete Error", error)
			);
}

@Override
public Observable<List<PlannedMeal>> getAllPlannedMeals() {
	return storedPlannedMeals;
}

@Override
public void deletePlannedMealsByDate(String date) {
	dao.deletePlannedMealsByDate(date)
			.subscribeOn(Schedulers.io())
			.subscribe(
					() -> Log.d("DEBUG", "Product Deleted"),
					error -> Log.e("DEBUG", "Delete Error", error)
			);
	
}
}
