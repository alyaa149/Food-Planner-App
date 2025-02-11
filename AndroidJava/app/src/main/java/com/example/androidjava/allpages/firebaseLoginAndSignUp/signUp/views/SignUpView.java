package com.example.androidjava.allpages.firebaseLoginAndSignUp.signUp.views;

public interface SignUpView {
void onSignUpSuccess();
void onSignUpFailure(String message);
void onGoogleSignInSuccess();
void onGoogleSignInFailure(String message);
}
