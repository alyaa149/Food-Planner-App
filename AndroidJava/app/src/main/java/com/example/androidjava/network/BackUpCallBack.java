package com.example.androidjava.network;

import com.example.androidjava.Models.PlannedMeal;

import java.util.List;

public interface BackUpCallBack {
void onSuccess(List<PlannedMeal> plannedMeals);
void onFailure(Exception e);
}
