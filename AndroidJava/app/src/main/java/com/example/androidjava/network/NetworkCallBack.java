package com.example.androidjava.network;

import com.example.androidjava.Models.MealResponse;

public interface NetworkCallBack {
void onSuccess(MealResponse response);
void onFailure(String errorMessage);
}
