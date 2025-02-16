package com.example.androidjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidjava.allpages.favorites.view.favoritesPage;
import com.example.androidjava.allpages.home.views.Home;
import com.example.androidjava.allpages.plan.view.plansFragment;
import com.example.androidjava.allpages.search.view.SearchFragment;

public class MainActivity extends AppCompatActivity {
LinearLayout navHome,navSearch,navFavorites,navPlans;
private NavController navController;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(R.layout.activity_main);
	ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
		Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
		v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
		return insets;
	});
	navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
	
	SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
	
	if (isLoggedIn) {
		navController.navigate(R.id.homeFragment);
	} else {
		navController.navigate(R.id.loginPage);
	}
	 navHome = findViewById(R.id.nav_home);
	 navFavorites = findViewById(R.id.nav_favorites);
	 navSearch = findViewById(R.id.nav_search);
	 navPlans = findViewById(R.id.nav_plans);
	
	navHome.setOnClickListener(v -> {
		setActiveItem(navHome.findViewById(R.id.homeImg), navHome.findViewById(R.id.homeTV));
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
		navController.navigate(R.id.home2);
	});
	
	navFavorites.setOnClickListener(v -> {
		setActiveItem(navFavorites.findViewById(R.id.favImg), navFavorites.findViewById(R.id.favTV));
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
		navController.navigate(R.id.favoritesPage);
	});
	
	navSearch.setOnClickListener(v -> {
		setActiveItem(navSearch.findViewById(R.id.searchImg), navSearch.findViewById(R.id.searchTV));
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
		navController.navigate(R.id.searchFragment);
	});
	
	navPlans.setOnClickListener(v -> {
		setActiveItem(navPlans.findViewById(R.id.planImg), navPlans.findViewById(R.id.planTV));
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
		navController.navigate(R.id.plansFragment);
	});

	
	
	
}
private void loadFragment(Fragment fragment) {
	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction transaction = fragmentManager.beginTransaction();
	transaction.replace(R.id.nav_host_fragment_container, fragment);
	transaction.commit();
}
private void setActiveItem(ImageView icon, TextView text) {

	icon.setColorFilter(getResources().getColor(R.color.nav_item_active_color));
	text.setTextColor(getResources().getColor(R.color.nav_item_active_color));

}
}




