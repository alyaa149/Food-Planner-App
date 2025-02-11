package com.example.androidjava;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

Meal meal;


Retrofit retrofit;
ApiService apiService;
private static final String TAG = "FOOD";
private static final String url = "https://www.themealdb.com/api/json/v1/1/";

private List<Category> categoryList = new ArrayList<>();
List<Meal> meals = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(R.layout.activity_main);
	ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
		Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
		v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
		return insets;
	});
//	BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
//	BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//	NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
//
//	navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//
//		if ( destination.getId() == R.id.loginPage || destination.getId() == R.id.splashScreen || destination.getId() == R.id.introduction || destination.getId() == R.id.signUp2) {
//			bottomAppBar.setVisibility(View.GONE);
//		} else {
//			bottomAppBar.setVisibility(View.VISIBLE);
//		}
//	});
	
}



private void searchByName(String name) {
	Call<MealResponse> myCall = apiService.searchByName(name);
	myCall.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meals = response.body().getMeals();
				for (Meal meal1 : response.body().getMeals()) {
					Log.i("SEARCH", "Search result: " + meal1.getStrArea());
				}
				
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
			
		}
	});
	
}

}




