package com.example.androidjava.allpages.search.presenter;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.Models.Repository;
import com.example.androidjava.allpages.home.presenters.HomePresenter;
import com.example.androidjava.allpages.home.views.HomeView;
import com.example.androidjava.allpages.search.view.SearchView;
import com.example.androidjava.network.NetworkCallback;

public class SearchPresenterImpl implements SearchPresenter, NetworkCallback {
private SearchView searchView;
private Repository repository;

public SearchPresenterImpl(SearchView searchView, Repository repository) {
	this.searchView = searchView;
	this.repository = repository;
}






@Override
public void onSuccessMeal(MealResponse response) {
searchView.showMeals(response.getMeals());

}

@Override
public void onSuccess(CategoryResponse response) {

	
}

@Override
public void onSuccessCountry(MealResponse response) {

	
}

@Override
public void onFailure(String errorMessage) {
	searchView.showError(errorMessage);
	
}

@Override
public void searchMeals(String query) {
  repository.getMealsByName(query, this);
}

@Override
public void searchCategories(String query) {
repository.getMealsByCategory(query, this);
}

@Override
public void searchCountries(String query) {
repository.getMealsByCountry(query, this);
}

@Override
public void searchIngredients(String query) {
	repository.getMealsByIngredient(query, this);

}
}
