package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;

public interface RealTimeFireBaseCallBack {
void onSuccess();
void onFailure(Exception e);

}
