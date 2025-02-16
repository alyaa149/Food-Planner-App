package com.example.androidjava.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Utils.SharedStrings;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
private static ApiService apiService;
private FirebaseAuth auth;
private static MealsRemoteDataSourceImpl instance;
private static Retrofit retrofit;
private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";



public MealsRemoteDataSourceImpl() {
	retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
	apiService = retrofit.create(ApiService.class);
	auth = FirebaseAuth.getInstance();
}

@Override
public void getCategories(NetworkCallback callback) {
	apiService.getAllCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> callback.onSuccess(response), error -> callback.onFailure("Failed to fetch categories"));


//			.enqueue(new Callback<CategoryResponse>() {
//		@Override
//		public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//			callback.onSuccess(response.body());
//		}
//
//		@Override
//		public void onFailure(Call<CategoryResponse> call, Throwable t) {
//			callback.onFailure("Failed to fetch categories");
//		}
//	});
}

@Override
public void getCountries(NetworkCallback callback) {
	apiService.getAllAreas().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> callback.onSuccessCountry(response), error -> callback.onFailure("Failed to fetch countries"));
}

@Override
public void getRandomMeal(NetworkCallback callback) {
	apiService.getRandomMeal()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to getRandomMeal"));
}

@Override
public void getMealById(int id, NetworkCallback callback) {
	apiService.getMealById(id)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to getMealById"));
}

@Override
public void getMealByName(String name, NetworkCallback callback) {
	apiService.searchByName(name)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to search by meal"));
}

@Override
public void getMealByCategory(String category, NetworkCallback callback) {
	apiService.filterByCategory(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to fetch categories"));
}

@Override
public void getMealByArea(String area, NetworkCallback callback) {
	apiService.filterByArea(area).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to get meal by id"));
}

@Override
public void getMealByIngredient(String ingredient, NetworkCallback callback) {
	apiService.filterByIngredient(ingredient)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(response -> callback.onSuccessMeal(response), error -> callback.onFailure("Failed to get meal by inngredient"));
}

//@Override
//public void getMealByLetter(char letter, NetworkCallback callback) {
//  //apiService.filterByLetter(letter).subscribeOn(Schedulers.io())
//}

@Override
public void getMealBySearch(String query, NetworkCallback callback) {

}

@Override
public void createUserWithEmailAndPassword(String email, String password, AuthCallback callback) {
	Log.i("TAG", "data source: " + password + ", " + email);
	auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> callback.onSuccess("User registered successfully")).addOnFailureListener(e -> callback.onFailure(e.getMessage()));

//	FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//			.addOnCompleteListener(task -> {
//				if (task.isSuccessful()) {
//					callback.onSuccess("Sign-up successful!");
//				} else {
//					callback.onFailure(task.getException().getMessage());
//				}
//			});
}

@Override
public void signInWithEmailAndPassword(String email, String password, AuthCallback callback) {
	auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> callback.onSuccess("Login successful")).addOnFailureListener(e -> callback.onFailure(e.getMessage()));
}


@Override
public void signInAndSignUpWithGoogle(String token, AuthCallback callback) {
	AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
	auth.signInWithCredential(credential).addOnSuccessListener(authResult -> {
		if (authResult.getAdditionalUserInfo().isNewUser()) {
			callback.onSuccess("New Google user signed up");
		} else {
			callback.onSuccess("Existing Google user logged in");
		}
	}).addOnFailureListener(e -> callback.onFailure(e.getMessage()));
}

@Override
public void signOut(AuthCallback callback) {
	FirebaseAuth auth = FirebaseAuth.getInstance();
	

	auth.signOut();

	GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(auth.getApp().getApplicationContext());
	
	if (account != null) {
		GoogleSignIn.getClient(auth.getApp().getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
				.signOut()
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						callback.onSuccess("Google Sign-Out successful");
					} else {
						callback.onFailure("Google Sign-Out failed");
					}
				});
	} else {
		callback.onSuccess("Google Sign-Out successful");  // If not signed in with Google, just return success
	}
}

private DatabaseReference getDBUsersReference() {
	
	if (SharedStrings.auth.getCurrentUser() == null) {
		return null;
	}
	return FirebaseDatabase.getInstance().getReference("Meals").child(auth.getCurrentUser().getUid());
}



@Override
public void insertDBUsersPlanReference(int day, int month, int year, Meal meal, RealTimeFireBaseCallBack listener) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		listener.onFailure(new Exception("User is not authenticated"));
		return;
	}
	
	String dateKey = formatDateKey(day, month, year);
	dbRef.child("MyPlans").child(dateKey).child(meal.getIdMeal()).setValue(meal)
			.addOnSuccessListener(aVoid -> listener.onSuccess())
			.addOnFailureListener(listener::onFailure);
}

@Override
public void deleteDBUsersPlanReference(int day, int month, int year, Meal meal, RealTimeFireBaseCallBack listener) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		listener.onFailure(new Exception("User is not authenticated"));
		return;
	}
	
	String dateKey = formatDateKey(day, month, year);
	dbRef.child("MyPlans").child(dateKey).child(meal.getIdMeal()).removeValue()
			.addOnSuccessListener(aVoid -> listener.onSuccess())
			.addOnFailureListener(listener::onFailure);
}
private String formatDateKey(int day, int month, int year) {
	return day + " " + month + " " + year; // Format: "16/2/2025"
}

@Override
public void insertDBUsersFavReference(Meal meal, RealTimeFireBaseCallBack listener) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		listener.onFailure(new Exception("User is not authenticated"));
		return;
	}
	
	dbRef.child("MyFavs").child(meal.getIdMeal()).setValue(meal)
			.addOnSuccessListener(aVoid -> listener.onSuccess())
			.addOnFailureListener(listener::onFailure);
}

@Override
public void deleteDBUsersFavReference(Meal meal, RealTimeFireBaseCallBack listener) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		listener.onFailure(new Exception("User is not authenticated"));
		return;
	}
	
	dbRef.child("MyFavs").child(meal.getIdMeal()).removeValue()
			.addOnSuccessListener(aVoid -> listener.onSuccess())
			.addOnFailureListener(listener::onFailure);
}

@Override
public void getPlannedMealByDate(String userId, int day, int month, int year, AllMealsCallBackFirBase<List<PlannedMeal>> callback) {
	List<PlannedMeal> plannedMeals = new ArrayList<>();
	String dateKey = day + " " + month + " " + year;
	DatabaseReference dbRef = getDBUsersReference().child("MyPlans").child(dateKey);
	
	Log.d("DEBUG", "Fetching meals from Firebase: " + dateKey);
	
	dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
		@Override
		public void onDataChange(@NonNull DataSnapshot snapshot) {
			Log.d("DEBUG", "Firebase snapshot exists? " + snapshot.exists());
			Log.d("DEBUG", "Firebase snapshot children count: " + snapshot.getChildrenCount());
			
			for (DataSnapshot mealSnapshot : snapshot.getChildren()) {
				Log.d("DEBUG", "Snapshot Key: " + mealSnapshot.getKey());
				Log.d("DEBUG", "Snapshot Value: " + mealSnapshot.getValue());
				
				Meal meal = mealSnapshot.getValue(Meal.class);
				if (meal != null && meal.getIdMeal() != null) {
					PlannedMeal plannedMeal = new PlannedMeal(meal);
					plannedMeals.add(plannedMeal);
					Log.d("DEBUG", "Planned Meal exists: " + plannedMeal.getMeal().getIdMeal() + " - " + plannedMeal.getMeal().getStrMeal());
				} else {
					Log.e("DEBUG", "Skipping a null Meal object: " + mealSnapshot.getKey());
				}
			}
			
			if (plannedMeals.isEmpty()) {
				Log.d("DEBUG", "No planned meals found in Firebase.");
			}
			
			callback.onSuccess(plannedMeals);
		}
		
		@Override
		public void onCancelled(@NonNull DatabaseError error) {
			callback.onFailure(error.getMessage());
		}
	});
}



public void getAllFavMealsByFireBase(String userId, OnMealsLoadedListener listener) {
	DatabaseReference dbRef = getDBUsersReference().child("MyFavs");
	
	Log.d("DEBUG", "Fetching meals from: " + dbRef.toString());
	
	dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
		@Override
		public void onDataChange(@NonNull DataSnapshot snapshot) {
			List<Meal> favoriteMeals = new ArrayList<>();
			
			Log.d("DEBUG", "Snapshot exists: " + snapshot.exists());
			
			for (DataSnapshot mealSnapshot : snapshot.getChildren()) {
				Meal meal = mealSnapshot.getValue(Meal.class);
				if (meal != null) {
					Log.d("DEBUG", "Meal retrieved: " + meal.getStrMeal());
					favoriteMeals.add(meal);
				} else {
					Log.e("DEBUG", "Null meal detected!");
				}
			}
			
			Log.d("DEBUG", "Total meals fetched: " + favoriteMeals.size());
			listener.onMealsLoaded(favoriteMeals);
		}
		
		@Override
		public void onCancelled(@NonNull DatabaseError error) {
			Log.e("DEBUG", "Firebase error: " + error.getMessage());
			listener.onError(error.getMessage());
		}
	});
}


}