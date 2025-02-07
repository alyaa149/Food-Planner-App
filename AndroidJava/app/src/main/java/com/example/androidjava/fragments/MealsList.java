package com.example.androidjava.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidjava.Models.Meal;
import com.example.androidjava.R;
import com.example.androidjava.adapters.MealAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealsList extends Fragment {
RecyclerView recyclerView;
MealAdapter mealAdapter;
List<Meal> mealList =new ArrayList<>();

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;
String country ,category;
public MealsList() {
	// Required empty public constructor
}

/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment MealsList.
 */
// TODO: Rename and change types and number of parameters
public static MealsList newInstance(String param1, String param2) {
	MealsList fragment = new MealsList();
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
	
	
	return inflater.inflate(R.layout.fragment_meals_list, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	if (getArguments() == null) {
		Toast.makeText(getContext(), "Arguments are null", Toast.LENGTH_SHORT).show();
	} else {
		// Debug
//		for (String key : getArguments().keySet()) {
//			Toast.makeText(getContext(), "Bundle Key: " + key, Toast.LENGTH_SHORT).show();
//		}
//
		category=getArguments().getString("category");
		country = getArguments().getString("country");
		Toast.makeText(getContext(), "Selected Category: " + category, Toast.LENGTH_SHORT).show();
		Toast.makeText(getContext(), "Selected Country: " + country, Toast.LENGTH_SHORT).show();
	}
	super.onViewCreated(view, savedInstanceState);
}
}