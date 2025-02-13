package com.example.androidjava.allpages.search.view;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;

import java.util.List;

public interface SearchView {
	void showMeals(List<Meal> meals);
	void showError(String errorMessage);
}
