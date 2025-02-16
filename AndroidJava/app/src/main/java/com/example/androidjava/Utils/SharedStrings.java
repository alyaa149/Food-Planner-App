package com.example.androidjava.Utils;

import com.google.firebase.auth.FirebaseAuth;

public class SharedStrings {
public static String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
public  static FirebaseAuth auth = FirebaseAuth.getInstance();
}
