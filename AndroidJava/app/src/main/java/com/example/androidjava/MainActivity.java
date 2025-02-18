package com.example.androidjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidjava.allpages.favorites.view.favoritesPage;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.login.views.LoginPage;
import com.example.androidjava.allpages.home.views.Home;
import com.example.androidjava.allpages.plan.view.plansFragment;
import com.example.androidjava.allpages.search.view.SearchFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
LinearLayout navHome,navSearch,navFavorites,navPlans;
//private NavController navController;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
			                                                    .findFragmentById(R.id.nav_host_fragment_container);
	
	if (navHostFragment == null) {
		Log.e("NavController", "NavHostFragment is NULL! Check activity_main.xml.");
		return;
	}
	
	NavController navController = navHostFragment.getNavController();
	
	Log.i("DEBUG", "isUserLoggedIn in main activity" + isUserLoggedIn());
	if (isUserLoggedIn()) {
		navController.navigate(R.id.home2);
		
	} else {
	navController.navigate(R.id.splashScreen);
	}
	CardView bottomNav = findViewById(R.id.navigation_bar);
	
	
	navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
		if (destination.getId() == R.id.splashScreen ||
				    destination.getId() == R.id.introduction ||
				    destination.getId() == R.id.loginPage ||
				    destination.getId() == R.id.signUp2) {
			bottomNav.setVisibility(View.GONE);
		} else {
			bottomNav.setVisibility(View.VISIBLE);
		}
	});
	
	setupNavigation(navController);
}
private boolean isUserLoggedIn() {
	SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
	return sharedPreferences.getBoolean("isLoggedIn", false);
}
private void setupNavigation(NavController navController) {
	findViewById(R.id.nav_home).setOnClickListener(v -> {
		Log.d("Navigation", "Navigating to Home");
		navController.navigate(R.id.home2);
	});
	
	findViewById(R.id.nav_favorites).setOnClickListener(v -> {

		if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
			navController.navigate(R.id.favoritesPage);
		}else {
			Snackbar.make(findViewById(R.id.nav_favorites), "Please login first", Snackbar.LENGTH_SHORT).show();
		}
	});
	
	findViewById(R.id.nav_search).setOnClickListener(v -> {
	;
		navController.navigate(R.id.searchFragment);
	});
	
	findViewById(R.id.nav_plans).setOnClickListener(v -> {
		if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
			navController.navigate(R.id.plansFragment);
		}else{
			Snackbar.make(findViewById(R.id.nav_favorites), "Please login first", Snackbar.LENGTH_SHORT).show();
			
		}
	});
}
private void setActiveItem(ImageView icon, TextView text) {

	icon.setColorFilter(getResources().getColor(R.color.nav_item_active_color));
	text.setTextColor(getResources().getColor(R.color.nav_item_active_color));

}
}




