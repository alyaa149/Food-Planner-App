package com.example.androidjava.network;

public interface AllMealsCallBackFirBase <T>{
void onSuccess(T data);
void onFailure(String error);
}
