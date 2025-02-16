package com.example.androidjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class SplashScreen extends Fragment {

private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

private String mParam1;
private String mParam2;
TextView appName;
View view;
LottieAnimationView lottieAnimationView;

public SplashScreen() {
}

public static SplashScreen newInstance(String param1, String param2) {
	SplashScreen fragment = new SplashScreen();
	Bundle args = new Bundle();
	args.putString(ARG_PARAM1, param1);
	args.putString(ARG_PARAM2, param2);
	fragment.setArguments(args);
	return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
		mParam1 = getArguments().getString(ARG_PARAM1);
		mParam2 = getArguments().getString(ARG_PARAM2);
	}
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
	appName = view.findViewById(R.id.appName);
	lottieAnimationView = view.findViewById(R.id.lottie);
	
	appName.animate()
			.translationY(-1000)
			.setDuration(2700)
			.setStartDelay(0);
	
	lottieAnimationView.animate()
			.translationX(2000)
			.setDuration(2000)
			.setStartDelay(2900);
	
	new Handler().postDelayed(() -> {
		Navigation.findNavController(view).navigate(R.id.action_splashScreen_to_introduction);
	}, 5000);
}
}
