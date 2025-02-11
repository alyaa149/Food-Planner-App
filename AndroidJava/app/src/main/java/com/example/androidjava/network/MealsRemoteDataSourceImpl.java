package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealRepository;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.home.views.HomeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
private static ApiService apiService;
private static MealsRemoteDataSourceImpl instance;
private static Retrofit retrofit;
private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";


public MealsRemoteDataSourceImpl() {
	retrofit = new Retrofit.Builder()
			           .baseUrl(BASE_URL)
			           .addConverterFactory(GsonConverterFactory.create())
			           .build();
	apiService = retrofit.create(ApiService.class);
}

@Override
public void getCategories(NetworkCallback callback) {
	apiService.getAllCategories().enqueue(new Callback<CategoryResponse>() {
		@Override
		public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
			callback.onSuccess(response.body());
		}
		
		@Override
		public void onFailure(Call<CategoryResponse> call, Throwable t) {
			callback.onFailure("Failed to fetch categories");
		}
	});
}

@Override
public void getCountries(NetworkCallback callback) {
	apiService.getAllAreas().enqueue(
			
			new Callback<MealResponse>() {
				@Override
				public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
					callback.onSuccessCountry(response.body());
				}
				
				@Override
				public void onFailure(Call<MealResponse> call, Throwable t) {
					callback.onFailure("Failed to fetch countries");
					
				}
			}
	);
}

@Override
public void getRandomMeal(NetworkCallback callback) {
	apiService.getRandomMeal().enqueue(
			new Callback<MealResponse>() {
				@Override
				public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
					callback.onSuccessMeal(response.body());
				}
				
				@Override
				public void onFailure(Call<MealResponse> call, Throwable t) {
					callback.onFailure("Failed to fetch random meal");
				}
			}
	
	);
}

@Override
public void getMealById(int id, NetworkCallback callback) {
apiService.getMealById(id).enqueue(
		new Callback<MealResponse>() {
			@Override
			public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
				callback.onSuccessMeal(response.body());
			}
			
			@Override
			public void onFailure(Call<MealResponse> call, Throwable t) {
				callback.onFailure("Failed to fetch countries");
			}
		}
);
}

@Override
public void getMealByName(String name, NetworkCallback callback) {

}

@Override
public void getMealByCategory(String category, NetworkCallback callback) {
apiService.filterByCategory(category).enqueue(
	
				
				new Callback<MealResponse>() {
					@Override
					public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
						callback.onSuccessMeal(response.body());
					}
					
					@Override
					public void onFailure(Call<MealResponse> call, Throwable t) {
						callback.onFailure("Failed to fetch countries");
						
					}}
				
		
);
}

@Override
public void getMealByArea(String area, NetworkCallback callback) {
apiService.filterByArea(area).enqueue(
		new Callback<MealResponse>() {
			@Override
			public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
				callback.onSuccessMeal(response.body());
			}
			
			@Override
			public void onFailure(Call<MealResponse> call, Throwable t) {
				callback.onFailure("Failed to fetch countries");
			}
		}
);
}

@Override
public void getMealByIngredient(String ingredient, NetworkCallback callback) {
apiService.filterByIngredient(ingredient);
}

@Override
public void getMealByLetter(char letter, NetworkCallback callback) {

}

@Override
public void getMealBySearch(String query, NetworkCallback callback) {

}
}