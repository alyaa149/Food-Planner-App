package com.example.androidjava.allpages.firebaseLoginAndSignUp;

import android.util.Log;

import com.example.androidjava.Models.Repository;

public class AuthPresenterImpl implements AuthPresenter{
private AuthView view;
private Repository repository;
public AuthPresenterImpl(AuthView view, Repository repository) {
	this.view = view;
	this.repository = repository;
}
@Override
public void signUp(String email, String password) {
	Log.i("TAG", "presenter: " + password+", "+email);
	
	repository.signUp(email, password, new AuthCallback() {
		@Override
		public void onSuccess(String message) {
			
			view.onAuthSuccess(message);
		}
		
		@Override
		public void onFailure(String error) {
			
			view.onAuthFailure(error);
		}
	});
}

@Override
public void signIn(String email, String password) {
	
	repository.signIn(email, password, new AuthCallback() {
		@Override
		public void onSuccess(String message) {
			view.onAuthSuccess(message);
		}
		
		@Override
		public void onFailure(String error) {
			view.onAuthFailure(error);
		}
	});
}

@Override
public void signInWithGoogle(String token) {
	
	repository.signInWithGoogle(token, new AuthCallback() {
		@Override
		public void onSuccess(String message) {
			view.onAuthSuccess(message);
		}
		
		@Override
		public void onFailure(String error) {
			view.onAuthFailure(error);
		}
	});
}

@Override
public void signOut() {
	repository.signOut(new AuthCallback() {
		@Override
		public void onSuccess(String message) {
			view.onAuthSuccess(message);
		}
		
		@Override
		public void onFailure(String error) {
			view.onAuthFailure(error);
		}
	});
}
}
