package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

import java.util.List;

public interface RealTimeFireBaseCallBack {
void onSuccess();
void onFailure(Exception e);
void onSuccess(List<PlannedMeal> meals);

}
