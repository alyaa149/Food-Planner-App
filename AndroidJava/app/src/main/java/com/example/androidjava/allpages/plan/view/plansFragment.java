package com.example.androidjava.allpages.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link plansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class plansFragment extends Fragment  implements PlansView{

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;
RepositoryImpl repository;
MealsLocalDataSourceImp localDataSource;
MealsRemoteDataSourceImpl remoteDataSource;


public plansFragment() {
	// Required empty public constructor
}


public static plansFragment newInstance(String param1, String param2) {
	plansFragment fragment = new plansFragment();
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
	remoteDataSource = new MealsRemoteDataSourceImpl();
	localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	
	
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

	return inflater.inflate(R.layout.fragment_plans, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
}

@Override
public void showMeals(List<PlannedMeal> meals) {
	Log.d("DEBUG", "first Meals received in plans: " + meals.get(0).getDate());
}

@Override
public void showError(String message) {
	Log.d("DEBUG", "Error in plans: " + message);
}
}