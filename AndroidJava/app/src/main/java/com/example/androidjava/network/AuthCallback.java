package com.example.androidjava.network;

import com.example.androidjava.Models.CategoryResponse;

public interface AuthCallback {
void onSuccess(String response);
void onFailure(String errorMessage);

}
