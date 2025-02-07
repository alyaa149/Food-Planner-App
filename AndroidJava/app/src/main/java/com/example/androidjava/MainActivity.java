package com.example.androidjava;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

Meal meal ;



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
	

	//=======================================================================
	 retrofit = new Retrofit.Builder()
			                    .baseUrl(url)
			                    .addConverterFactory(GsonConverterFactory.create())
			                    .build();
	 apiService = retrofit.create(ApiService.class);
	getAllCountries();
	getAllCategories();
	getMealInfo();
	searchByIngredient("chicken");
	searchByCategory("Seafood");
	searchByArea("Canadian");
	getRandomMeal();
	getMealById(52772);
	searchByName("Arrabiata");
	
}
private  void getRandomMeal(){
	Call<MealResponse> call = apiService.getRandomMeal();
	call.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meals = response.body().getMeals();
				for (Meal meal : response.body().getMeals()) {
					Log.i("SEARCH", "Random Meal Name: " + meal.getStrMeal());
				}
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
		}
	});

}
private void searchByIngredient(String ingredient){
	
	Call<MealResponse> call = apiService.filterByIngredient(ingredient);
	call.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				 meals = response.body().getMeals();
				for (Meal meal : response.body().getMeals()) {
					Log.i("SEARCH", "Meal Name: " + meal.getStrMeal());
				}
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
		}
	});
}
private void searchByCategory(String category){
	
	Call<MealResponse> call = apiService.filterByCategory(category);
	call.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meals = response.body().getMeals();
				for (Meal meal : response.body().getMeals()) {
					Log.i("SEARCH", "Search by category: " + meal.getStrMeal());
				}
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
		}
	});
}

private void searchByArea(String area){
	
	Call<MealResponse> call = apiService.filterByArea(area);
	call.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meals = response.body().getMeals();
				for (Meal meal : response.body().getMeals()) {
					Log.i("SEARCH", "Search by area: " + meal.getStrMeal());
				}
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
		}
	});
}
private  void getMealInfo(){

}
private void getAllCategories() {
	Call<CategoryResponse> myCall =apiService.getAllCategories();
	myCall.enqueue(new Callback<CategoryResponse>() {
		@Override
		public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				categoryList = response.body().getCategories();
				
				
				
				//adapter = new MyAdapter(MainActivity.this, countryList);
				//	recyclerView.setAdapter(adapter);
			}for (Category cat : response.body().getCategories()) {
				Log.i("LIST", "Category: " + cat.getStrCategory());
			}
		}
		
		@Override
		public void onFailure(Call<CategoryResponse> call, Throwable t) {
			Log.i(TAG, "Error: " + t.getMessage());
		}
		
		
	
		
		
	});

}
private void getAllCountries(){
	Call<MealResponse> myCall =apiService.getAllAreas();
	myCall.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meals = response.body().getMeals();
				for (Meal area : response.body().getMeals()) {
						Log.i("COUNTRY", "Country/Area: " + area.getStrArea());
				}
				
				
				//adapter = new MyAdapter(MainActivity.this, countryList);
			//	recyclerView.setAdapter(adapter);
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i(TAG, "Error: " + t.getMessage());
			
		}
		
		
	});
	
}
private void getMealById(int id){
	Call<MealResponse> myCall =apiService.getMealById(id);
	myCall.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				meal = response.body().getMeals().get(0);
					Log.i("SEARCH", "get the meal by id: " + meal.getStrMeal());
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("bf", "Error: " + t.getMessage());
		}
		
	});
	
}

private void searchByName(String name){
	Call<MealResponse> myCall=apiService.searchByName(name);
	myCall.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
		if(response.isSuccessful() && response.body()!=null){
		meals=response.body().getMeals();
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
private void  showPhoto(String img){
//	Glide.with(context)
//			.load(place)
//			.into(photo);
//
}
//@Override
//protected void onResume() {
//	super.onResume();
//
//	checkCurUser();
//}


}