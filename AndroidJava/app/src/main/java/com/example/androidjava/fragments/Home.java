package com.example.androidjava.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidjava.R;

public class Home extends Fragment {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";


private String mParam1;
private String mParam2;

public Home() {

}


public static Home newInstance(String param1, String param2) {
	Home fragment = new Home();
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
	// Inflate the layout for this fragment
	return inflater.inflate(R.layout.fragment_home, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	CardView randomMealCard = view.findViewById(R.id.includedMealCell);


	ImageView mealImage = randomMealCard.findViewById(R.id.foodImg);
	TextView mealName = randomMealCard.findViewById(R.id.Title);
	TextView  mealDesc = randomMealCard.findViewById(R.id.desc);


	mealName.setText("Pasta Carbonara");
	mealImage.setImageResource(R.drawable.fav);
	super.onViewCreated(view, savedInstanceState);
}
}