package com.example.androidjava.firebaseLoginAndSignUp;

import com.google.firebase.auth.AuthResult;

public interface FireBaseCallBack {
public void onSuccess(AuthResult authResult);
public void onFailure(Exception e);

}
