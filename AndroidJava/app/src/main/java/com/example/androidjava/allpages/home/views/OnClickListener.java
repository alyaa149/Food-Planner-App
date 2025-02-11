package com.example.androidjava.allpages.home.views;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.FavMeal;
import com.example.androidjava.Models.Meal;

public interface OnClickListener {
	void onFavClick(FavMeal favMeal);
void onCountryClick(String country);
void onCategoryListener(Category category);


}
