package com.example.androidjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Introduction extends Fragment {
Button btnSignIn;
Button btnRegister;

private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;
LinearLayout linearLayout;

public Introduction() {

}


public static Introduction newInstance(String param1, String param2) {
	Introduction fragment = new Introduction();
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
	return inflater.inflate(R.layout.fragment_introduction, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	
	btnRegister=view.findViewById(R.id.btnLogin);
	btnSignIn=view.findViewById(R.id.btnSignIn);
	
	btnSignIn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Navigation.findNavController(view).navigate(R.id.action_introduction_to_loginPage);
		}
	});
	btnRegister.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Navigation.findNavController(view).navigate(R.id.action_introduction_to_signUp2);

		}
	});
	super.onViewCreated(view, savedInstanceState);
}
}