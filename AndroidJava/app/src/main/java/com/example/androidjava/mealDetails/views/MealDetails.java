package com.example.androidjava.mealDetails.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealRepositoryImpl;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
//import com.example.androidjava.adapters.StepAdapter;
import com.example.androidjava.mealDetails.presenters.MealDetailsPresenter;
import com.example.androidjava.mealDetails.presenters.MealDetailsPresenterImpl;
import com.example.androidjava.mealsList.presenters.MealsListPresenter;
import com.example.androidjava.mealsList.presenters.MealsListPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetails extends Fragment implements MealDetailsView {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";


private String mParam1;
private String mParam2;
WebView webView;
RecyclerView recyclerView;

IngrediantAdapter ingrediantAdapter;


Meal meal;
String mealId;
private MealDetailsPresenterImpl mealDetailsPresenterImpl ;
MealRepositoryImpl repository;

TextView mealArea, mealName, steps;
ImageView mealImg;
View view;
List<Meal> meals = new ArrayList<>();
Map<String, String> ingredientsDetails = new HashMap<>();

public MealDetails() {

}


public static MealDetails newInstance(String param1, String param2) {
	MealDetails fragment = new MealDetails();
	Bundle args = new Bundle();
	args.putString(ARG_PARAM1, param1);
	args.putString(ARG_PARAM2, param2);
	fragment.setArguments(args);
	return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
		mParam1 = getArguments().getString(ARG_PARAM1);
		mParam2 = getArguments().getString(ARG_PARAM2);
	}
	MealsRemoteDataSourceImpl remoteDataSource = new MealsRemoteDataSourceImpl();
	repository = new MealRepositoryImpl(remoteDataSource);
	mealDetailsPresenterImpl = new MealDetailsPresenterImpl(repository, this);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_meal_details, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	getTheMealFromArguments();
//	Toast.makeText(getContext(), "Meal ingredient0 : " + meal.getStrIngredient1(), Toast.LENGTH_SHORT).show();
	init();
	Toast.makeText(getContext(), "Meal id : " + Integer.parseInt(mealId), Toast.LENGTH_SHORT).show();
	mealDetailsPresenterImpl.getMealDetails(Integer.parseInt(mealId));
	
	
}

public void init() {
	recyclerView = view.findViewById(R.id.recyclerView);
	//stepRecyclerView=view.findViewById(R.id.stepsRecyclerView);
	mealName = view.findViewById(R.id.mealName);
	mealArea = view.findViewById(R.id.country);
	mealImg = view.findViewById(R.id.mealImage);
	steps = view.findViewById(R.id.steps);
	webView = view.findViewById(R.id.mealVideo);
}


private void showIngredients() {
	Map<String, String> ingredientsDetails = new HashMap<>();
	
	try {
		for (int i = 1; i <= 20; i++) {
			
			Field ingredientField = Meal.class.getDeclaredField("strIngredient" + i);
			ingredientField.setAccessible(true);
			String ingredient = (String) ingredientField.get(meal);
			
			
			String thumbnailUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
			
			
			if (ingredient != null && !ingredient.isEmpty() && thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
				ingredientsDetails.put(ingredient, thumbnailUrl);
			}
		}
		
		
		if (!ingredientsDetails.isEmpty()) {
			ingrediantAdapter = new IngrediantAdapter(getContext(), ingredientsDetails);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			recyclerView.setAdapter(ingrediantAdapter);
			ingrediantAdapter.notifyDataSetChanged();
		}
	} catch (Exception e) {
		e.printStackTrace(); // Handle reflection errors
	}
}


private void updateUI() {
	if (meal != null) {
		
		mealName.setText(meal.getStrMeal());
		mealArea.setText("." + meal.getStrArea());
		steps.setText(meal.getStrInstructions());
		Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImg);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(meal.getStrYoutube());
		
		
	}
}

private void getTheMealFromArguments() {
	Bundle bundle = getArguments();
	if (bundle != null) {
		mealId = (String) bundle.getString("name");
	} else {
		Toast.makeText(getContext(), "Bundle is null", Toast.LENGTH_SHORT).show();
	}
}


@Override
public void showMealDetails(Meal meal) {
	this.meal = meal;
	updateUI();
	showIngredients();
//	ingrediantAdapter.notifyDataSetChanged();
}



@Override
public void showError(String message) {
	Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
}
}