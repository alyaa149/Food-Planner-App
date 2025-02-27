package com.example.androidjava.alldata.localdata;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.alldata.localdata.PlanDAO;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSourceImp implements MealsLocalDataSource {
private PlanDAO dao;
private FavDAO favDao;
private static MealsLocalDataSourceImp localDataSource = null;
Observable<List<Meal>> storedFavMeals;


private MealsLocalDataSourceImp(Context context) {
	AppDataBase db = AppDataBase.getInstanse(context.getApplicationContext());
	favDao = db.getMealDao();
	dao = db.getPlanMealDao();
}

public static MealsLocalDataSourceImp getInstance(Context context) {
	if (localDataSource == null) {
		localDataSource = new MealsLocalDataSourceImp(context);
	}
	return localDataSource;
}
@Override
public void insertMeals(List<Meal> meals) {
	favDao.clearMeals();
	 favDao.insertMeals(meals)
			       .subscribeOn(Schedulers.io())
			       .observeOn(AndroidSchedulers.mainThread())
			 .subscribe(
					 ()-> Log.d("DEBUG", "Meals Inserted"),
					 error -> Log.e("DEBUG", "Insert Error", error)
			 );
}
	@Override
public void insert(Meal meal) {
	Log.i("DEBUG", "insert id in local ds: " + meal.getIdMeal());
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
public Observable<List<Meal>> getAllMeals(String userId) {
	return favDao.getAllFavoriteMeals(userId);
}

@Override
public void insertPlannedMeal(PlannedMeal meal) {
	dao.insertPlannedMeal(meal)
			.subscribeOn(Schedulers.io())
			.subscribe(
					() -> Log.d("DEBUG", "planned meal Inserted"),
					error -> Log.e("DEBUG", "Insert Error", error)
			);
	
}
@Override
public void insertPlannedMeals(List<PlannedMeal> meals) {
	dao.insertPlannedMeals(meals)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
					()-> Log.d("DEBUG", "Meals Inserted"),
					error -> Log.e("DEBUG", "Insert Error", error)
			);
}

@Override
public void deletePlannedMealsByDate(String date) {
//	dao.deletePlannedMeal(meal)
//			.subscribeOn(Schedulers.io())
//			.subscribe(
//					() -> Log.d("DEBUG", "planned meal Deleted"),
//					error -> Log.e("DEBUG", "Delete Error", error)
//			);
}

@Override
public Observable<List<PlannedMeal>> getAllPlannedMeals(String userId) {
	Log.d("DEBUG", "DAO method getAllPlannedMeals() called for user: " + userId);
	return dao.getAllPlannedMeals(userId);
}

@Override
public Observable<List<PlannedMeal>> getPlannedMealByDate(String userId, int day, int month, int year) {
	return dao.getPlannedMealByDate(userId, day, month, year);
}

@Override
public void deletePlannedMeal(String userId, String mealId, int day, int month, int year) {
	dao.deletePlannedMealsByDate(userId,mealId,day,month,year)
			.subscribeOn(Schedulers.io())
			.subscribe(
					() -> Log.d("DEBUG", "Product Deleted"),
					error -> Log.e("DEBUG", "Delete Error", error)
			);
	
}
}
