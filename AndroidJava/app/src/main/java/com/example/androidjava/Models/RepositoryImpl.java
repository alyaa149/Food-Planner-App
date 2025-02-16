package com.example.androidjava.Models;

import android.util.Log;

import com.example.androidjava.alldata.localdata.MealsLocalDataSource;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthCallback;
import com.example.androidjava.network.MealsRemoteDataSource;
import com.example.androidjava.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RepositoryImpl implements Repository {

private MealsRemoteDataSource  remoteDataSource;
private MealsLocalDataSource localDataSource;
public RepositoryImpl(MealsRemoteDataSource remoteDataSource,MealsLocalDataSource localDataSource) {

this.remoteDataSource = remoteDataSource;
this.localDataSource = localDataSource;

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
public void getMealsByIngredient(String ingredient, NetworkCallback callback) {
	remoteDataSource.getMealByIngredient(ingredient, callback);
}

@Override
public void getMealsByName(String name, NetworkCallback callback) {
	remoteDataSource.getMealByName(name, callback);
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

@Override
public void signUp(String email, String password, AuthCallback callback) {
	remoteDataSource.createUserWithEmailAndPassword(email, password, callback);
}

@Override
public void signIn(String email, String password, AuthCallback callback) {
	Log.i("TAG", "repository: " + password+", "+email);
	
	remoteDataSource.signInWithEmailAndPassword(email, password, callback);
}

@Override
public void signInWithGoogle(String token, AuthCallback callback) {
remoteDataSource.signInAndSignUpWithGoogle(token, callback);
}

@Override
public void signOut(AuthCallback callback) {
remoteDataSource.signOut(callback);
}

@Override
public void addToFavorites(Meal meal) {
	localDataSource.insert(meal);
	
}

@Override
public Observable<List<Meal>> getAllFavorites(String userId) {
	return localDataSource.getAllMeals(userId);
}

@Override
public void delete(Meal meal) {
	localDataSource.delete(meal);
	
}

@Override
public void insertPlannedMeal(PlannedMeal meal) {
	localDataSource.insertPlannedMeal(meal);
	
}

@Override
public void deletePlannedMeal(String userId, String mealId, int day, int month, int year) {
	localDataSource.deletePlannedMeal(userId, mealId, day, month, year);
}

@Override
public Observable<List<PlannedMeal>> getAllPlannedMeals(String userId) {
	return localDataSource.getAllPlannedMeals(userId);
}

@Override
public Observable<List<PlannedMeal>> getPlannedMealByDate(String userId, int day, int month, int year) {
	return localDataSource.getPlannedMealByDate(userId, day, month, year);
}

@Override
public void deletePlannedMealsByDate(String date) {
	localDataSource.deletePlannedMealsByDate(date);
}
}
