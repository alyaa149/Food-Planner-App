package com.example.androidjava.Models;

import com.example.androidjava.db.localdata.MealsLocalDataSource;
import com.example.androidjava.network.ApiService;
import com.example.androidjava.network.MealsRemoteDataSource;
import com.example.androidjava.network.NetworkCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepositoryImpl implements MealRepository {

private MealsRemoteDataSource  remoteDataSource;
private MealsLocalDataSource localDataSource;
public MealRepositoryImpl(MealsRemoteDataSource remoteDataSource) {

this.remoteDataSource = remoteDataSource;
//this.localDataSource = localDataSource;

}

@Override
public void getAllCategories(NetworkCallback callback) {
	remoteDataSource.getCategories(callback);
	

}

@Override
public void getAllCountries(NetworkCallback callback) {
	remoteDataSource.getCountries(callback);

}


@Override
public void getRandomMeal(NetworkCallback callback) {
 remoteDataSource.getRandomMeal(callback);
}

@Override
public void getMealsByCategory(String category, NetworkCallback callback) {
	remoteDataSource.getMealByCategory(category, callback);
}

@Override
public void getMealsByCountry(String country, NetworkCallback callback) {
	remoteDataSource.getMealByArea(country, callback);
}

@Override
public void getMealById(int mealId, NetworkCallback callback) {
	remoteDataSource.getMealById(mealId,callback);
}
}
