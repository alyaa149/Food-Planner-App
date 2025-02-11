package com.example.androidjava.allpages.firebaseLoginAndSignUp;

public interface AuthView {
void onAuthSuccess(String message);
void onAuthFailure(String error);
}
