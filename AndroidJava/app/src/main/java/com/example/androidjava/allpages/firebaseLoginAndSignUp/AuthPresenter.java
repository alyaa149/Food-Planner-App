package com.example.androidjava.allpages.firebaseLoginAndSignUp;

public interface AuthPresenter {
void signUp(String email, String password);
void signIn(String email, String password);
void signInWithGoogle(String token);
void signOut();
}
