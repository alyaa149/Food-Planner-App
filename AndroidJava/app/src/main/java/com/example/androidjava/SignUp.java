package com.example.androidjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends Fragment {
TextInputEditText passET;
TextInputEditText emailTE;
Button signUpBtn;
LinearLayout logInTV;
LinearLayout registerWithGoogleET;
FirebaseAuth auth;
FirebaseUser curUser;
GoogleSignInClient googleSignInUser;

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;

public SignUp() {
	// Required empty public constructor
}

/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment SignUp.
 */
// TODO: Rename and change types and number of parameters
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
	// Inflate the layout for this fragment
	return inflater.inflate(R.layout.fragment_sign_up, container, false);
}


@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	
	passET = view.findViewById(R.id.passET);
	logInTV = view.findViewById(R.id.loginTV);
	emailTE = view.findViewById(R.id.emailTE);
	signUpBtn = view.findViewById(R.id.loginBtn);
	registerWithGoogleET = view.findViewById(R.id.registerWithGoogleET);
	
	auth = FirebaseAuth.getInstance();
	
	// Initialize Google Sign-In
	GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			                          .requestIdToken(getString(R.string.default_web_client_id))  // Use the correct Web Client ID
			                          .requestEmail()
			                          .build();
	
	googleSignInUser = GoogleSignIn.getClient(requireContext(), gso); // Use requireContext() inside Fragment
	
	signUpBtn.setOnClickListener(v -> setSignUpBtn(view));
	registerWithGoogleET.setOnClickListener(v -> setLoginWithGoogleBtn());
	
	logInTV.setOnClickListener(view2 ->
			                           Navigation.findNavController(view2).navigate(R.id.action_signUp2_to_loginPage)
	);
	//	loginBtn =findViewById(R.id.loginBtn);

//	logOutBtn=findViewById(R.id.logOutBtn);
//	loginWithGoogleBtn=findViewById(R.id.loginWithGoogleBtn);
//	//logOutWithGoogleBtn=findViewById(R.id.logOutWithGoogleBtn);

//	loginBtn.setOnClickListener(view -> setLoginBtn());
//	logOutBtn.setOnClickListener(view -> setLogOutBtn());
//	loginWithGoogleBtn.setOnClickListener(view -> setLoginWithGoogleBtn());
//	//logOutWithGoogleBtn.setOnClickListener(view -> setLogOutBtn());
//
//	GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//			                         .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
//	googleSignInUser= GoogleSignIn.getClient(this,gso);
}


private void setSignUpBtn(View view){
	String password = passET.getText().toString();
	String email = emailTE.getText().toString();
	if (email.isEmpty() || password.isEmpty()) {
		Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_LONG).show();
		return;
	}
	auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
		@Override
		public void onSuccess(AuthResult authResult) {
			Navigation.findNavController(view).navigate(R.id.action_signUp2_to_home2);
			
			//	Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();

		}
	}).addOnFailureListener(new OnFailureListener() {
		@Override
		public void onFailure(@NonNull Exception e) {
			Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
		}
	});


}
//private  void setLogOutBtn(){
//	auth.signOut(); //remove cache from user mobile
//
//
//
//}
//private void setLogOutBtn(){
//	//googleSignInUser.signOut();
//	auth.signOut();
//
//}

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




}