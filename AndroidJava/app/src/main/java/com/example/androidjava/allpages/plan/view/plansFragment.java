package com.example.androidjava.allpages.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.mealsList.views.MealAdapter;
import com.example.androidjava.allpages.mealsList.views.OnMealClickListener;
import com.example.androidjava.allpages.plan.presenter.PlanPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link plansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class plansFragment extends Fragment  implements PlansView,OnDayClickListener, OnMealClickListener {

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
List<String> days =new ArrayList<>();
List<Meal> mealsOfTheDay;
View view;

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;
MealAdapter mealAdapter;
RecyclerView recyclerView;
PlanAdapter planAdapter;

RepositoryImpl repository;
MealsLocalDataSourceImp localDataSource;
MealsRemoteDataSourceImpl remoteDataSource;
PlanPresenterImpl plansPresenter;



public plansFragment() {
	// Required empty public constructor
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
	plansPresenter.getPlannedMeals();
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
view = inflater.inflate(R.layout.fragment_plans, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	getNext7Days();
//plansPresenter.removeMealFromPlanned(FirebaseAuth.getInstance().getUid().toString() ,"53040",16,02,2025);

}

@Override
public void showMeals(List<PlannedMeal> meals) {

	planAdapter = new PlanAdapter(getContext(), days,this);
	recyclerView = view.findViewById(R.id.recyclerView2);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
	recyclerView.setAdapter(planAdapter);
	Log.d("DEBUG", "showMeals() called. Meals count: " + meals.size());
meals.forEach(meal -> Log.d("DEBUG", "Meal in plans: " + meal.getMeal().getStrMeal()+"mealId "+meal.getMeal().getIdMeal()+" year : "+meal.getYear()));
}

@Override
public void showMealsByDate(List<Meal> meals) {
	mealAdapter = new MealAdapter(getContext(),meals,this);
	recyclerView = view.findViewById(R.id.planrecyclerView);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
	recyclerView.setAdapter(mealAdapter);
	Log.d("DEBUG", "Total Meals: " + meals.size());
}

public  void getNext7Days() {
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
		Toast.makeText(getContext(), "Meal click ->" +meal.getStrMeal(), Toast.LENGTH_SHORT).show();
		
		plansPresenter.addMealToFavorites(meal);
		//Navigation.findNavController(view).navigate(R.id.action_mealsList_to_favoritesPage);
	}

}



@Override
public void onDayClick(int day, int month, int year) {
	Log.d("DEBUG", "onDayClick() called with: day = [" + day + "], month = [" + month + "], year = [" + year + "]");
	plansPresenter.getMealsByDay(day,month,year);
}
}