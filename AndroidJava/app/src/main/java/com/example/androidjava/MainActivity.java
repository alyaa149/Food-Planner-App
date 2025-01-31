package com.example.androidjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button loginBtn;
Button signUpBtn;
Button logOutBtn;
FirebaseAuth auth;
FirebaseUser curUser;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(R.layout.activity_main);
	auth=FirebaseAuth.getInstance();
	loginBtn =findViewById(R.id.loginBtn);
	signUpBtn=findViewById(R.id.signUpBtn);
	logOutBtn=findViewById(R.id.logOutBtn);
	signUpBtn.setOnClickListener(view -> setSignUpBtn());
	loginBtn.setOnClickListener(view -> setLoginBtn());
	logOutBtn.setOnClickListener(view -> setLogOutBtn());
	
	
	
	
	
}
@Override
protected void onResume() {
	super.onResume();
	
	checkCurUser();
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
private  void setLogOutBtn(){
	auth.signOut(); //remove cache from user mobile
	


}
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