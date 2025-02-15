package com.example.androidjava.allpages.firebaseLoginAndSignUp;

public interface AuthCallback {
void onSuccess(String response);
void onFailure(String errorMessage);

}
