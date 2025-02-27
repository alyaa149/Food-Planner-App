package com.example.androidjava.allpages.firebaseLoginAndSignUp.login.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.androidjava.Models.Repository;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSource;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends Fragment implements AuthView {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
private AuthPresenter presenter;
FirebaseAuth auth;
FirebaseUser curUser;
Button loginBtn;
Repository repository;
TextInputEditText passET;
TextInputEditText emailTE;
LinearLayout loginWithGoogleET;
GoogleSignInClient googleSignInUser;

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;

public LoginPage() {
	// Required empty public constructor
}


// TODO: Rename and change types and number of parameters
public static LoginPage newInstance(String param1, String param2) {
	LoginPage fragment = new LoginPage();
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
	return inflater.inflate(R.layout.fragment_login_page, container, false);
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	auth = FirebaseAuth.getInstance();
	passET = view.findViewById(R.id.passET);
	MealsRemoteDataSourceImpl remoteDataSource = new MealsRemoteDataSourceImpl();
//	String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
	MealsLocalDataSourceImp localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	
	presenter = new AuthPresenterImpl(this, repository);
	loginWithGoogleET = view.findViewById(R.id.loginWithGoogleET);
	loginWithGoogleET.setOnClickListener(v -> initiateGoogleSignIn());
	emailTE = view.findViewById(R.id.emailTE);
	loginBtn = view.findViewById(R.id.loginBtn);
	loginBtn.setOnClickListener(v -> presenter.signIn(emailTE.getText().toString(), passET.getText().toString()));
//	loginBtn.setOnClickListener(v -> presenter.signIn("2@g.com", "123456"));
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
private void saveUserLoginStatus(Context context, boolean isLoggedIn) {
	SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = sharedPreferences.edit();
	editor.putBoolean("isLoggedIn", isLoggedIn);
	editor.apply();
	
}

@Override
public void onAuthSuccess(String message) {
	if (isAdded()) {
		saveUserLoginStatus(getContext(), true);
		Navigation.findNavController(requireView()).navigate(R.id.action_loginPage_to_home2);
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
		
	}
}

@Override
public void onAuthFailure(String error) {
	if (isAdded()) {
		Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
	}
}
}