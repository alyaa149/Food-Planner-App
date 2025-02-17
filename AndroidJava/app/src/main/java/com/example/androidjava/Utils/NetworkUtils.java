package com.example.androidjava.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
public static boolean isNetworkAvailable(Context context) {
	ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
	return activeNetwork != null && activeNetwork.isConnected();
}
}
