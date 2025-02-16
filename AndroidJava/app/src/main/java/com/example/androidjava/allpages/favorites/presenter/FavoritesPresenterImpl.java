package com.example.androidjava.allpages.favorites.presenter;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.allpages.favorites.view.FavoritesView;
import com.example.androidjava.network.OnMealsLoadedListener;
import com.example.androidjava.network.RealTimeFireBaseCallBack;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenterImpl implements FavoritesPresenter {
private FavoritesView favoritesView;
private RepositoryImpl mealsRepository;

public FavoritesPresenterImpl(FavoritesView favoritesView, RepositoryImpl mealsRepository) {
	this.favoritesView = favoritesView;
	this.mealsRepository = mealsRepository;
}

@Override
public void getFavorites() {
	mealsRepository.getAllFavorites(FirebaseAuth.getInstance().getCurrentUser().getUid()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(products -> favoritesView.showFavProducts(products), error -> favoritesView.showError(error.getMessage()));
	
}

@Override
public void removeMealFromFavorites(Meal meal) {
	mealsRepository.delete(meal);
	
}

@Override
public void deleteFavMealFireBase(Meal meal) {
	mealsRepository.deleteDBUsersFavReference(meal, new RealTimeFireBaseCallBack() {
		@Override
		public void onSuccess() {
			favoritesView.showFireBaseSuccess("Meal added successfully!");
		}
		
		@Override
		public void onFailure(Exception e) {
			favoritesView.showError("Error: " + e.getMessage());
		}
	});
	
}

@Override
public void getFavoritesFromFireBase() {
	String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
	
	mealsRepository.getAllFavoriteMeals(userId, new OnMealsLoadedListener() {
		@Override
		public void onMealsLoaded(List<Meal> meals) {
			favoritesView.showFavProductsFireBase(meals);
		}
		
		@Override
		public void onError(String errorMessage) {
			favoritesView.showError(errorMessage);
		}
		
	
	});
}

}
