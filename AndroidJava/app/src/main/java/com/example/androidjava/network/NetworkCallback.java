package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.MealResponse;

public interface NetworkCallback {
void onSuccessMeal(MealResponse response);
void onSuccess(CategoryResponse response);
void onSuccessCountry(MealResponse response);
void onFailure(String errorMessage);
}
