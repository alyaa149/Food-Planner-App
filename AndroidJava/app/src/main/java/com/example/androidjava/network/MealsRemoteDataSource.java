package com.example.androidjava.network;

import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthCallback;

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


}
