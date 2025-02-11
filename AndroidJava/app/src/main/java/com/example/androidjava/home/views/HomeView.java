package com.example.androidjava.home.views;

import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;

import java.util.List;

public interface HomeView {
void showCategories(List<Category> categories);
void showCountries(List<Meal> areas);
void showRandomMeal(Meal meal);
void showError(String message);
}
