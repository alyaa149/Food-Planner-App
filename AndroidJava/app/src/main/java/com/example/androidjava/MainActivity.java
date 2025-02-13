package com.example.androidjava;

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

import com.example.androidjava.allpages.favorites.view.favoritesPage;
import com.example.androidjava.allpages.home.views.Home;
import com.example.androidjava.allpages.plan.view.plansFragment;
import com.example.androidjava.allpages.search.view.SearchFragment;

public class MainActivity extends AppCompatActivity {
LinearLayout navHome,navSearch,navFavorites,navPlans;

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
	 navHome = findViewById(R.id.nav_home);
	 navFavorites = findViewById(R.id.nav_favorites);
	 navSearch = findViewById(R.id.nav_search);
	 navPlans = findViewById(R.id.nav_plans);

	
	navHome.setOnClickListener(v -> {
		loadFragment(new Home());
		setActiveItem(v.findViewById(R.id.homeImg), v.findViewById(R.id.homeTV));
	});
	
	navFavorites.setOnClickListener(v -> {
		loadFragment(new favoritesPage());
	//	setActiveItem(v.findViewById(R.id.), v.findViewById(R.id.));
	});

	navSearch.setOnClickListener(v -> {
		loadFragment(new SearchFragment());
	//	setActiveItem(v.findViewById(R.id.), v.findViewById(R.id.));
	});

	navPlans.setOnClickListener(v -> {
		loadFragment(new plansFragment());
	//	setActiveItem(v.findViewById(R.id.), v.findViewById(R.id.));
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




