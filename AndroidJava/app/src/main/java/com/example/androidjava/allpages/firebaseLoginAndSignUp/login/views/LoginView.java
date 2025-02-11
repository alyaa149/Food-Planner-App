package com.example.androidjava.allpages.firebaseLoginAndSignUp.login.views;

public interface LoginView {
void onSignInSuccess();
void onSignInFailure(String message);
void onGoogleSignInSuccess();
void onGoogleSignInFailure(String message);
}
