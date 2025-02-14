package com.example.androidjava.allpages.favorites.presenter;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.allpages.favorites.view.FavoritesView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenterImpl implements FavoritesPresenter {
private FavoritesView favoritesView;
private RepositoryImpl mealsRepository;
public FavoritesPresenterImpl(FavoritesView favoritesView, RepositoryImpl mealsRepository) {
	this.favoritesView=favoritesView;
	this.mealsRepository=mealsRepository;
}
@Override
public void getFavorites() {
	mealsRepository.getAllFavorites()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
					products -> favoritesView.showFavProducts(products),
					error -> favoritesView.showError(error.getMessage())
			);

}

@Override
public void removeMealFromFavorites(Meal meal) {
	mealsRepository.delete(meal);

}
}
