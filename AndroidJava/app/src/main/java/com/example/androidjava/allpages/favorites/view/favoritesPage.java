package com.example.androidjava.allpages.favorites.view;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.favorites.presenter.FavoritesPresenter;
import com.example.androidjava.allpages.favorites.presenter.FavoritesPresenterImpl;
import com.example.androidjava.allpages.mealsList.presenters.MealsListPresenterImpl;
import com.example.androidjava.allpages.mealsList.views.MealAdapter;
import com.example.androidjava.allpages.mealsList.views.MealsList;
import com.example.androidjava.allpages.mealsList.views.OnMealClickListener;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class favoritesPage extends Fragment implements FavoritesView , OnMealClickListener {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
View view;


private String mParam1;
RepositoryImpl repository;
List<Meal> mealList =new ArrayList<>();
FavoritesPresenter favoritesPresenter;
private String mParam2;
RecyclerView recyclerView;
MealAdapter mealAdapter;
TextView emptyFavTV;

public favoritesPage() {

}


public static favoritesPage newInstance(String param1, String param2) {
	favoritesPage fragment = new favoritesPage();
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
	favoritesPresenter = new FavoritesPresenterImpl(this, repository) ;
//	favoritesPresenter.getFavorites();
	favoritesPresenter.getFavoritesFromFireBase();
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_favorites_page, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	this.view = view;
	emptyFavTV =view.findViewById(R.id.emptyFavTV);
	recyclerView = view.findViewById(R.id.recyclerViewFav);
	mealAdapter = new MealAdapter(getContext(), mealList, this);
	//recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	recyclerView.setAdapter(mealAdapter);
	recyclerView.post(() -> {
		Log.d("DEBUG", "RecyclerView child count: " + recyclerView.getChildCount());
	});
	checkEmptyList();

}
private void checkEmptyList() {
	if(mealList.isEmpty()){
		emptyFavTV.setVisibility(View.VISIBLE);
	}else{
		emptyFavTV.setVisibility(View.GONE);
	}
}

@Override
public void showFavProducts(List<Meal> meals) {
//	if(isAdded()) {
//		Log.d("DEBUG", "Meals received: " + meals.size());
//		Toast.makeText(getContext(), "Meals received: " + meals.size(), Toast.LENGTH_SHORT).show();
//
//		if (meals == null || meals.isEmpty()) {
//			Toast.makeText(getContext(), "No meals received!", Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		mealList.clear();
//		mealList.addAll(meals);
//		mealAdapter.notifyDataSetChanged();
//		checkEmptyList();
//
//		recyclerView.post(() -> {
//			recyclerView.invalidate();
//			recyclerView.requestLayout();
//			Log.d("DEBUG", "RecyclerView forced to relayout");
//		});
//		Log.d("DEBUG", "RecyclerView is visible");
//	}
}

@Override
public void showFavProductsFireBase(List<Meal> meals) {
	if (isAdded() && getContext() != null) {
		Toast.makeText(getContext(), "Meals received: " + meals.size(), Toast.LENGTH_SHORT).show();
	}
	
	Log.d("DEBUG", "showFavProductsFireBase called with " + meals.size() + " meals");
	
	if (meals == null || meals.isEmpty()) {
		Log.e("DEBUG", "No meals received!");
		Toast.makeText(getContext(), "No meals received!", Toast.LENGTH_SHORT).show();
		return;
	}
	
	mealList.clear();
	mealList.addAll(meals);
	mealAdapter.notifyDataSetChanged();
	Log.d("DEBUG", "Meal adapter updated!");
	
	recyclerView.post(() -> {
		recyclerView.invalidate();
		recyclerView.requestLayout();
		Log.d("DEBUG", "RecyclerView forced to relayout");
	});
}



@Override
public void showError(String message) {
	Log.i("DEBUG", "Error message: " + message);
}

@Override
public void showFireBaseSuccess(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
	
}

@Override
public void onMealClick(Meal meal) {
	Bundle bundle = new Bundle();
	if (meal == null || meal.getIdMeal() == null) {
		Log.i("DEBUG", "Meal is null or id is null");
		return;
	}
	
	bundle.putString("name", meal.getIdMeal());
	Navigation.findNavController(view).navigate(R.id.action_favoritesPage_to_mealDetails, bundle);
}

@Override
public void onFavClick(Meal meal) {
	if (isAdded()) {
		Toast.makeText(getContext(), "Meal click ->" +meal.getStrMeal(), Toast.LENGTH_SHORT).show();
		favoritesPresenter.removeMealFromFavorites(meal);
		favoritesPresenter.deleteFavMealFireBase(meal);
		mealAdapter.notifyDataSetChanged();
		checkEmptyList();
		//Navigation.findNavController(view).navigate(R.id.action_mealsList_to_favoritesPage);
	}
}
}