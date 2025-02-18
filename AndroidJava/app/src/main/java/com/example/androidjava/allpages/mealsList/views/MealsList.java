package com.example.androidjava.allpages.mealsList.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.mealsList.presenters.MealsListPresenter;
import com.example.androidjava.allpages.mealsList.presenters.MealsListPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class MealsList extends Fragment implements OnMealClickListener , MealsListView {
RecyclerView recyclerView;
MealAdapter mealAdapter;
List<Meal> mealList =new ArrayList<>();
private MealsListPresenter mealsListPresenter ;
RepositoryImpl repository;



private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

View view;
private String mParam1;
private String mParam2;
String country ,category;
public MealsList() {

}


public static MealsList newInstance(String param1, String param2) {
	MealsList fragment = new MealsList();
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
	
	MealsLocalDataSourceImp localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	mealsListPresenter = new MealsListPresenterImpl(this, repository);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_meals_list, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	FirebaseAuth auth = FirebaseAuth.getInstance();
	FirebaseUser user = auth.getCurrentUser();
	

	
	if (getArguments() == null) {
		Toast.makeText(getContext(), "Arguments are null", Toast.LENGTH_SHORT).show();
	} else {
		category = getArguments().getString("category");
		country = getArguments().getString("country");
		
//		Toast.makeText(getContext(), "Selected Category form mealslist: " + category, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getContext(), "Selected Country: from mealslist " + country, Toast.LENGTH_SHORT).show();
//
		
		recyclerView = view.findViewById(R.id.recyclerView);
		mealAdapter = new MealAdapter(getContext(), mealList, MealsList.this);
		//recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(mealAdapter);
		recyclerView.post(() -> {
			Log.d("DEBUG", "RecyclerView child count: " + recyclerView.getChildCount());
		});
		
		
		
		if (category != null ) {
Log.i("DEBUG", "category != null " + category);
			mealsListPresenter.getMealsByCategory(category);
			
		
		} else if (country != null ) {
Log.i("DEBUG", "country != null " + country);
mealsListPresenter.getMealsByCountry(country);
	
		}
	}
}

@Override
public void onMealClick(Meal meal) {
	Bundle bundle = new Bundle();
	if (meal == null || meal.getIdMeal() == null) {
Log.i("DEBUG", "Meal is null or id is null");
return;
	}
	
	bundle.putString("name", meal.getIdMeal());
	Navigation.findNavController(view).navigate(R.id.action_mealsList_to_mealDetails, bundle);
}

@Override
public void onFavClick(Meal meal) {
	
	if (isAdded()) {
		Toast.makeText(getContext(), "Meal click ->" +meal.getStrMeal() + meal.getStrArea(), Toast.LENGTH_SHORT).show();


		mealsListPresenter.addToFavFireBase(meal);
	//	mealsListPresenter.removeFromFavFireBase(meal);
		mealAdapter.notifyDataSetChanged();
		//Navigation.findNavController(view).navigate(R.id.action_mealsList_to_favoritesPage);
	}
	
}


@Override
public void showMeals(List<Meal> meals) {
	if(isAdded()) {
		Log.d("DEBUG", "Meals received: " + meals.size());
	//	Toast.makeText(getContext(), "Meals received: " + meals.size(), Toast.LENGTH_SHORT).show();
		
		if (meals == null || meals.isEmpty()) {
			Toast.makeText(getContext(), "No meals received!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mealList.clear();
		mealList.addAll(meals);
		mealAdapter.notifyDataSetChanged(); // Ensure adapter knows the data changed
		
		recyclerView.post(() -> {
			recyclerView.invalidate();
			recyclerView.requestLayout();
			Log.d("DEBUG", "RecyclerView forced to relayout");
		});
		Log.d("DEBUG", "RecyclerView is visible");
	}
}

@Override
public void showError(String message) {
Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
}

@Override
public void showSuccess(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
	
}
}