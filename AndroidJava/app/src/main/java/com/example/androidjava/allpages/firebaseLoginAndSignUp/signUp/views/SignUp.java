package com.example.androidjava.allpages.firebaseLoginAndSignUp.signUp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthPresenter;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthPresenterImpl;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthView;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends Fragment implements AuthView {
TextInputEditText passET,emailTE;
AuthPresenter presenter;
Button signUpBtn;
RepositoryImpl repository;
LinearLayout logInTV,registerWithGoogleET;
FirebaseAuth auth;
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
private String mParam1;
private String mParam2;
private String email;
private String password;

public SignUp() {
}

public static SignUp newInstance(String param1, String param2) {
	SignUp fragment = new SignUp();
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
	return inflater.inflate(R.layout.fragment_sign_up, container, false);
}


@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
	passET = view.findViewById(R.id.passET);
	logInTV = view.findViewById(R.id.loginTV);
	emailTE = view.findViewById(R.id.emailTE);
	signUpBtn = view.findViewById(R.id.loginBtn);
	registerWithGoogleET = view.findViewById(R.id.loginWithGoogleET);
	auth = FirebaseAuth.getInstance();
	MealsRemoteDataSourceImpl remoteDataSource = new MealsRemoteDataSourceImpl();
	
	MealsLocalDataSourceImp localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	presenter = new AuthPresenterImpl(this, repository);
	//Log.i("TAG", "onViewCreated: " + email);
	logInTV.setOnClickListener(v->
	{
		//presenter.signOut();
		Navigation.findNavController(requireView()).navigate(R.id.action_signUp2_to_loginPage);
		
	}
	
	);
	signUpBtn.setOnClickListener(v -> {
		 email = emailTE.getText().toString();
		 password = passET.getText().toString();
		Log.i("TAG", "onViewCreated: " + email);
		presenter.signUp(email, password);});
	registerWithGoogleET.setOnClickListener(v -> initiateGoogleSignIn());
}

private void initiateGoogleSignIn() {
	GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			                          .requestIdToken(getString(R.string.default_web_client_id))
			                          .requestEmail()
			                          .build();
	GoogleSignInClient googleSignInUser = GoogleSignIn.getClient(requireContext(), gso);
	
	Intent signInGoogleIntent = googleSignInUser.getSignInIntent();
	startActivityForResult(signInGoogleIntent, 100);
}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	if (requestCode == 100) {
		Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
		try {
			GoogleSignInAccount account = task.getResult(ApiException.class);
			if (account != null) {
				String token = account.getIdToken();
				Log.i("GoogleAuth", "Google Sign-Up successful, Token: " + token);
				presenter.signInWithGoogle(token);
			} else {
				Log.e("GoogleAuth", "Google Sign-Up failed, account is null");
			}
		} catch (ApiException e) {
			Log.e("GoogleAuth", "Google Sign-Up failed: " + e.getMessage());
		}
	}
}


@Override
public void onAuthSuccess(String message) {
	if(isAdded()) {
		Navigation.findNavController(requireView()).navigate(R.id.action_signUp2_to_home2);
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
	}
}

@Override
public void onAuthFailure(String error) {
	if(isAdded()){
		Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
	}
}
}
//
//@Override
//public void onSignUpSuccess() {
//Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
//Navigation.findNavController(requireView()).navigate(R.id.action_signUp2_to_home2);
//}
//
//@Override
//public void onSignUpFailure(String message) {
//Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//}
//
//@Override
//public void onGoogleSignInSuccess() {
//Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
//Navigation.findNavController(requireView()).navigate(R.id.action_signUp2_to_home2);
//}
//
//@Override
//public void onGoogleSignInFailure(String message) {
//Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//}
