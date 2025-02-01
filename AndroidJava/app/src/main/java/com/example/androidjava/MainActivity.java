package com.example.androidjava;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
Button loginBtn;
Button signUpBtn;
Button logOutBtn;
Button loginWithGoogleBtn;
//Button logOutWithGoogleBtn;

FirebaseAuth auth;
FirebaseUser curUser;
GoogleSignInClient googleSignInUser;
Retrofit retrofit;
ApiService apiService;
private static final String TAG = "FOOD";
private static final String url = "https://www.themealdb.com/api/json/v1/1/";
private List<Country> countryList = new ArrayList<>();
private List<Category> categoryList = new ArrayList<>();
List<Meal> meals = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(R.layout.activity_main);
	auth=FirebaseAuth.getInstance();
	loginBtn =findViewById(R.id.loginBtn);
	signUpBtn=findViewById(R.id.signUpBtn);
	logOutBtn=findViewById(R.id.logOutBtn);
	loginWithGoogleBtn=findViewById(R.id.loginWithGoogleBtn);
	//logOutWithGoogleBtn=findViewById(R.id.logOutWithGoogleBtn);
	signUpBtn.setOnClickListener(view -> setSignUpBtn());
	loginBtn.setOnClickListener(view -> setLoginBtn());
	logOutBtn.setOnClickListener(view -> setLogOutBtn());
	loginWithGoogleBtn.setOnClickListener(view -> setLoginWithGoogleBtn());
	//logOutWithGoogleBtn.setOnClickListener(view -> setLogOutBtn());
	
	GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			                         .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
	googleSignInUser= GoogleSignIn.getClient(this,gso);
	//=======================================================================
	 retrofit = new Retrofit.Builder()
			                    .baseUrl(url)
			                    .addConverterFactory(GsonConverterFactory.create())
			                    .build();
	 apiService = retrofit.create(ApiService.class);
	getAllCountries();
	getAllCategories();
	getMealInfo();
	searchByIngredient("chicken");
	
	
	
}
private void searchByIngredient(String ingredient){
	
	Call<MealResponse> call = apiService.filterByIngredient(ingredient);
	call.enqueue(new Callback<MealResponse>() {
		@Override
		public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				 meals = response.body().getMeals();
				for (Meal meal : response.body().getMeals()) {
					Log.i("SEARCH", "Meal Name: " + meal.getMealName());
				}
			}
		}
		
		@Override
		public void onFailure(Call<MealResponse> call, Throwable t) {
			Log.i("SEARCH", "Error: " + t.getMessage());
		}
	});
}
private  void getMealInfo(){

}
private void getAllCategories() {
	Call<CategoryResponse> myCall =apiService.getAllCategories();
	myCall.enqueue(new Callback<CategoryResponse>() {
		@Override
		public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				categoryList = response.body().getCategories();
				
				
				
				//adapter = new MyAdapter(MainActivity.this, countryList);
				//	recyclerView.setAdapter(adapter);
			}for (Category cat : response.body().getCategories()) {
				Log.i(TAG, "Category: " + cat.getStrCategory());
			}
		}
		
		@Override
		public void onFailure(Call<CategoryResponse> call, Throwable t) {
			Log.i(TAG, "Error: " + t.getMessage());
		}
		
		
	
		
		
	});

}
private void getAllCountries(){
	
	Call<CountryResponse> myCall =apiService.getAllAreas();
	myCall.enqueue(new Callback<CountryResponse>() {
		@Override
		public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
			if (response.isSuccessful() && response.body() != null) {
				countryList = response.body().getCountries();
				for (Country area : response.body().getCountries()) {
					Log.i(TAG, "Country/Area: " + area.getStrArea());
				}
				
				
				//adapter = new MyAdapter(MainActivity.this, countryList);
			//	recyclerView.setAdapter(adapter);
			}
		}
		
		@Override
		public void onFailure(Call<CountryResponse> call, Throwable t) {
			Log.i(TAG, "Error: " + t.getMessage());
		}
		
		
	});
	
}

@Override
protected void onResume() {
	super.onResume();
	
	checkCurUser();
}
private void setLoginWithGoogleBtn(){
	Intent signInGoogleIntent = googleSignInUser.getSignInIntent();
	startActivityForResult(signInGoogleIntent,5);
}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 5) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				GoogleSignInAccount acc = task.getResult(ApiException.class);
				signInWithGoogle(acc.getIdToken());
			} catch (ApiException e) {
				Toast.makeText(MainActivity.this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

	


private void signInWithGoogle(String token){
	AuthCredential credential= GoogleAuthProvider.getCredential(token,null);
	auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
		@Override
		public void onSuccess(AuthResult authResult) {
			Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
			
		}
	}).addOnFailureListener(new OnFailureListener() {
		@Override
		public void onFailure(@NonNull Exception e) {
			Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
		}
	});
}
private void setLogOutBtn(){
googleSignInUser.signOut();
auth.signOut();

}
private  void checkCurUser(){
	curUser = auth.getCurrentUser();
	if(curUser == null){
		Log.i("USERNOTEXIST","USER NOT EXIST");
		Toast.makeText(MainActivity.this,"user not exist",Toast.LENGTH_LONG).show();
		
	}else{
		Log.i("USEREXIST","USER EXIST");
		Toast.makeText(MainActivity.this,"user  exist",Toast.LENGTH_LONG).show();
		
	}
}
private void setSignUpBtn(){
auth.createUserWithEmailAndPassword("aliaa@gmail.com","123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
	@Override
	public void onSuccess(AuthResult authResult) {
		Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
		
	}
}).addOnFailureListener(new OnFailureListener() {
	@Override
	public void onFailure(@NonNull Exception e) {
		Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
	}
});


}
//private  void setLogOutBtn(){
//	auth.signOut(); //remove cache from user mobile
//
//
//
//}
private void setLoginBtn(){
	auth.signInWithEmailAndPassword("alia@gmail.com","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
		@Override
		public void onComplete(@NonNull Task<AuthResult> task) {
			if (task.isSuccessful()) {
				runOnUiThread(() ->
						              Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show()
				);
			} else {
				runOnUiThread(() ->
						              Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show()
				);
			}
		}
		});


	
}

}