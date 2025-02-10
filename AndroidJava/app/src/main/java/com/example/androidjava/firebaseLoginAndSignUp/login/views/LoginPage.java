package com.example.androidjava.firebaseLoginAndSignUp.login.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.androidjava.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends Fragment {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
FirebaseAuth auth;
FirebaseUser curUser;
Button loginBtn;
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
	
	loginWithGoogleET =view.findViewById(R.id.loginWithGoogleET);
	loginWithGoogleET.setOnClickListener(v-> setLoginWithGoogleBtn());
	emailTE = view.findViewById(R.id.emailTE);
	loginBtn =view.findViewById(R.id.loginBtn);

//	logOutBtn=findViewById(R.id.logOutBtn);
	//loginWithGoogleBtn=view.findViewById(R.id.loginWithGoogleBtn);
//	//logOutWithGoogleBtn=findViewById(R.id.logOutWithGoogleBtn);

	loginBtn.setOnClickListener(this::setLoginBtn);
//	logOutBtn.setOnClickListener(view -> setLogOutBtn());
//	//logOutWithGoogleBtn.setOnClickListener(view -> setLogOutBtn());

	GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			                          .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
	googleSignInUser = GoogleSignIn.getClient(getContext(), gso);
	super.onViewCreated(view, savedInstanceState);
}

private void setLoginWithGoogleBtn() {
	Intent signInGoogleIntent = googleSignInUser.getSignInIntent();
	googleSignInLauncher.launch(signInGoogleIntent);  // Use the new launcher
}

private final ActivityResultLauncher<Intent> googleSignInLauncher =
		registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
			if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
				Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
				try {
					GoogleSignInAccount acc = task.getResult(ApiException.class);
					signInWithGoogle(acc.getIdToken());  // Handle Google Sign-In
				} catch (ApiException e) {
					// Handle failure
				}
			}
		});




private void signInWithGoogle(String token) {
	AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
	auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
		@Override
		public void onSuccess(AuthResult authResult) {
			//Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
			
		}
	}).addOnFailureListener(new OnFailureListener() {
		@Override
		public void onFailure(@NonNull Exception e) {
			//Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
		}
	});
}
private void checkCurUser() {
	curUser = auth.getCurrentUser();
	if (curUser == null) {
		Log.i("USERNOTEXIST", "USER NOT EXIST");
		Toast.makeText(getContext(),"user not exist",Toast.LENGTH_LONG).show();
		
	} else {
		Log.i("USEREXIST", "USER EXIST");
		Toast.makeText(getContext(),"user  exist", Toast.LENGTH_LONG).show();
		
	}
}

private void setLoginBtn(View v) {
	String password = passET.getText().toString();
	String email = emailTE.getText().toString();
	auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
		@Override
		public void onComplete(@NonNull Task<AuthResult> task) {
			if (task.isSuccessful()) {
				Navigation.findNavController(v).navigate(R.id.action_loginPage_to_home2);
			} else {
			Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
			}
		}
	});
	
	
}


}