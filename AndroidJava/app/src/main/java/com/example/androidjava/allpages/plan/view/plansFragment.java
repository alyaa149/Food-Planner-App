package com.example.androidjava.allpages.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.Utils.NetworkUtils;
import com.example.androidjava.Utils.SharedStrings;
import com.example.androidjava.alldata.localdata.AppDataBase;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.mealsList.views.MealAdapter;
import com.example.androidjava.allpages.mealsList.views.OnMealClickListener;
import com.example.androidjava.allpages.plan.presenter.PlanPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;


public class plansFragment extends Fragment implements PlansView, OnDayClickListener, OnMealClickListener {

private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

int day, month, year, curDay, curMonth, curYear;
List<String> days = new ArrayList<>();
List<Meal> mealsOfTheDay;
View view;

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;
MealAdapter mealAdapter;
RecyclerView recyclerView;
DayPlanAdapter dayPlanAdapter;

RepositoryImpl repository;
MealsLocalDataSourceImp localDataSource;
MealsRemoteDataSourceImpl remoteDataSource;
PlanPresenterImpl plansPresenter;
List<PlannedMeal> plannedMeals;


public plansFragment() {
}


public static plansFragment newInstance(String param1, String param2) {
	plansFragment fragment = new plansFragment();
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
	remoteDataSource = new MealsRemoteDataSourceImpl();
	localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	plansPresenter = new PlanPresenterImpl(this, repository);
	
	
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_plans, container, false);
	getNext7Days();
	dayPlanAdapter = new DayPlanAdapter(getContext(), days, this);
	recyclerView = view.findViewById(R.id.recyclerView2);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
	recyclerView.setAdapter(dayPlanAdapter);
	
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
	
}

@Override
public void showAllMealsFromRoom(List<PlannedMeal> plannedMeals) {
	this.plannedMeals = plannedMeals;
}

@Override
public void showMealsByDate(List<Meal> meals) {
	if (mealAdapter == null) {
		mealAdapter = new MealAdapter(getContext(), meals, this);
		recyclerView = view.findViewById(R.id.planrecyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(mealAdapter);
	}else {
		mealAdapter.updateMeals(meals);
	}
	Log.d("DEBUG", "Total Meals: " + meals.size());
}

public void getNext7Days() {
	days.clear();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
	Calendar calendar = Calendar.getInstance();
	
	for (int i = 0; i < 7; i++) {
		days.add(sdf.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
	}
}


@Override
public void showError(String message) {
	Log.d("DEBUG", "Error in plans: " + message);
}

@Override
public void showSuccessFireBase(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
}

@Override
public void showMealsByFireBase(List<Meal> meals) {
	
	if (mealAdapter == null) {
		
		mealAdapter = new MealAdapter(getContext(), meals, this);
		recyclerView = view.findViewById(R.id.planrecyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(mealAdapter);
		
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
				return false;
			}
			
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
				int position = viewHolder.getAdapterPosition();
				Log.d("DEBUG", "Day: " + day + ", Month: " + month + ", Year: " + year);
				
				if (meals.isEmpty()) {
					Log.e("DEBUG", "Swipe ignored: Meals list is empty!");
					mealAdapter.notifyDataSetChanged();
					return;
				}
				
				if (position >= 0 && position < meals.size()) {
					Meal meal = meals.get(position);
					String mealId = meal.getIdMeal();
					Log.d("DEBUG", "Removing meal: " + mealId);
					plansPresenter.removeMealFromPlanned(mealId, day, month, year);
					plansPresenter.removeMealFromPlannedFireBase(day, month, year, meal);
					meals.remove(position);
					mealAdapter.notifyItemRemoved(position);
				} else {
					Log.e("DEBUG", "Invalid swipe position: " + position + ", List size: " + meals.size());
				}
			}
		}).attachToRecyclerView(recyclerView);
		
	} else {
		mealAdapter.updateMeals(meals);
		
	}
	
	Log.d("DEBUG", "Total Meals: " + meals.size());
	
}

@Override
public void showPlannedMeals(List<PlannedMeal> meals) {
	if (!meals.isEmpty()) {
		Log.d("DEBUG", "Received planned meals from Firebase backup: " + meals.get(0).getMealId() + meals.get(0).getDay() + meals.get(0).getMonth() + meals.get(0).getYear() + meals.get(0).getMeal().getStrMeal());
		plansPresenter.addMealsToRoom(meals);
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
	Navigation.findNavController(view).navigate(R.id.action_plansFragment_to_mealDetails, bundle);
}

@Override
public void onFavClick(Meal meal) {
	if (isAdded()) {
		
		plansPresenter.addMealToFavFireBase(meal);
	}
	
}


@Override
public void onDayClick(int day, int month, int year) {
	
	this.day = day;
	this.month = month;
	this.year = year;
	
	if (NetworkUtils.isNetworkAvailable(getContext())) {
		plansPresenter.getPlannedMealsByDate(FirebaseAuth.getInstance().getUid(), day, month, year);
		plansPresenter.fetchPlannedMeals();
	} else {
		plansPresenter.getMealsByDay(day, month, year);
	}
	if (mealAdapter != null) {
		mealAdapter.updateMeals(new ArrayList<>());
		mealAdapter.notifyDataSetChanged();
	}
	
}
}