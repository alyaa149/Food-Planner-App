package com.example.androidjava.Utils;

import com.example.androidjava.Models.Meal;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SharedFunctions {


public static DatabaseReference getDBUsersReference() {
	FirebaseAuth auth = FirebaseAuth.getInstance();
	if (auth.getCurrentUser() == null) {
		return null;
	}
	return FirebaseDatabase.getInstance().getReference("Meals")
			       .child(auth.getCurrentUser().getUid());
}


public static Task<Void> getDBUsersPlanReference(int day, int month, int year, Meal meal) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		return Tasks.forException(new Exception("User is not authenticated"));
	}
	return dbRef.child("MyPlans").child(String.valueOf(day) + String.valueOf(month) + String.valueOf(year)).child(meal.getIdMeal()).setValue(meal);
}

public static Task<Void> getDBUsersFavReference(Meal meal) {
	DatabaseReference dbRef = getDBUsersReference();
	if (dbRef == null) {
		return Tasks.forException(new Exception("User is not authenticated"));
	}
	return dbRef.child("MyFavs").child(meal.getIdMeal()).setValue(meal);
}
}
