package com.example.androidjava.network;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsRemoteDataSource {

	void getCategories(NetworkCallback callback);
	void getCountries(NetworkCallback callback);
	void getRandomMeal(NetworkCallback callback);
	void getMealById(int id, NetworkCallback callback);
	void getMealByName(String name, NetworkCallback callback);
	void getMealByCategory(String category, NetworkCallback callback);
	void getMealByArea(String area, NetworkCallback callback);
	void getMealByIngredient(String ingredient, NetworkCallback callback);
	//void getMealByLetter(char letter, NetworkCallback callback);
	void getMealBySearch(String query, NetworkCallback callback);
	void createUserWithEmailAndPassword(String email, String password, AuthCallback callback);
	void signInWithEmailAndPassword(String email, String password, AuthCallback callback);
	void signInAndSignUpWithGoogle(String token, AuthCallback callback);
	void signOut(AuthCallback callback);
   // DatabaseReference getDBUsersReference();
void insertDBUsersPlanReference(int day, int month, int year, Meal meal, RealTimeFireBaseCallBack callBack);
void deleteDBUsersPlanReference(int day, int month, int year, Meal meal, RealTimeFireBaseCallBack callBack);
void insertDBUsersFavReference(Meal meal,RealTimeFireBaseCallBack callBack);
void deleteDBUsersFavReference(Meal meal,RealTimeFireBaseCallBack callBack);
void getPlannedMealByDate(String userId, int day, int month, int year,AllMealsCallBackFirBase<List<PlannedMeal>> callback);
 void getAllFavMealsByFireBase(String userId, OnMealsLoadedListener listener);
public void getPlannedMeals(String userId, BackUpCallBack callback);



}
