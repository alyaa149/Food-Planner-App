//package com.example.androidjava;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link Login#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class Login extends Fragment {
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private static final String ARG_PARAM1 = "param1";
//private static final String ARG_PARAM2 = "param2";
//
//// TODO: Rename and change types of parameters
//private String mParam1;
//private String mParam2;
//Button loginBtn;
//Button signUpBtn;
//Button logOutBtn;

//Meal meal ;
//Button loginWithGoogleBtn;
////Button logOutWithGoogleBtn;
//
//public Login() {
//	// Required empty public constructor
//}
//
///**
// * Use this factory method to create a new instance of
// * this fragment using the provided parameters.
// *
// * @param param1 Parameter 1.
// * @param param2 Parameter 2.
// * @return A new instance of fragment Login.
// */
//// TODO: Rename and change types and number of parameters
//public static Login newInstance(String param1, String param2) {
//	Login fragment = new Login();
//	Bundle args = new Bundle();
//	args.putString(ARG_PARAM1, param1);
//	args.putString(ARG_PARAM2, param2);
//	fragment.setArguments(args);
//	return fragment;
//}
//
//@Override
//public void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//	if (getArguments() != null) {
//		mParam1 = getArguments().getString(ARG_PARAM1);
//		mParam2 = getArguments().getString(ARG_PARAM2);
//	}
//}
//
//@Override
//public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                         Bundle savedInstanceState) {
//	// Inflate the layout for this fragment
//	return inflater.inflate(R.layout.fragment_login2, container, false);
//}
////
////@Override
////public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
////
////	auth=FirebaseAuth.getInstance();
////	//loginBtn =view.findViewById(R.id.loginBtn);
//////	signUpBtn=findViewById(R.id.signUpBtn);
//////	logOutBtn=findViewById(R.id.logOutBtn);
////	//loginWithGoogleBtn=view.findViewById(R.id.loginWithGoogleBtn);
//////	//logOutWithGoogleBtn=findViewById(R.id.logOutWithGoogleBtn);
//////	signUpBtn.setOnClickListener(view -> setSignUpBtn());
////	loginBtn.setOnClickListener(view2 -> setLoginBtn());
//////	logOutBtn.setOnClickListener(view -> setLogOutBtn());
////	loginWithGoogleBtn.setOnClickListener(view2-> setLoginWithGoogleBtn());
//////	//logOutWithGoogleBtn.setOnClickListener(view -> setLogOutBtn());
//////
////	GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////			                         .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
////	googleSignInUser= GoogleSignIn.getClient(getContext(),gso);
////	super.onViewCreated(view, savedInstanceState);
////}
////private void setLoginWithGoogleBtn(){
////	Intent signInGoogleIntent = googleSignInUser.getSignInIntent();
////	startActivityForResult(signInGoogleIntent,5);
////}
//////
////////	@Override
////////	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////////		super.onActivityResult(requestCode, resultCode, data);
////////		if (requestCode == 5) {
////////			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
////////			try {
////////				GoogleSignInAccount acc = task.getResult(ApiException.class);
////////				signInWithGoogle(acc.getIdToken());
////////			} catch (ApiException e) {
////////				//Toast.makeText(MainActivity.this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////////			}
////////		}
////////	}
//////
//////
//////
////
////private void signInWithGoogle(String token){
////	AuthCredential credential= GoogleAuthProvider.getCredential(token,null);
////	auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
////		@Override
////		public void onSuccess(AuthResult authResult) {
////			//Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
////
////		}
////	}).addOnFailureListener(new OnFailureListener() {
////		@Override
////		public void onFailure(@NonNull Exception e) {
////			//Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
////		}
////	});
////}
////
////private  void checkCurUser(){
////	curUser = auth.getCurrentUser();
////	if(curUser == null){
////		Log.i("USERNOTEXIST","USER NOT EXIST");
////		//Toast.makeText(MainActivity.this,"user not exist",Toast.LENGTH_LONG).show();
////
////	}else{
////		Log.i("USEREXIST","USER EXIST");
////		//Toast.makeText(MainActivity.this,"user  exist",Toast.LENGTH_LONG).show();
////
////	}
////}
////
////private void setLoginBtn(){
////	auth.signInWithEmailAndPassword("alia@gmail.com","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////		@Override
////		public void onComplete(@NonNull Task<AuthResult> task) {
////			if (task.isSuccessful()) {
////
////			} else {
////
////			}
////		}
////	});
////
////
////
////}
//}