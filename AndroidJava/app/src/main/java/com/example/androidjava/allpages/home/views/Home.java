package com.example.androidjava.allpages.home.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.Repository;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.alldata.localdata.MealsLocalDataSource;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthPresenter;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthPresenterImpl;
import com.example.androidjava.allpages.firebaseLoginAndSignUp.AuthView;
import com.example.androidjava.allpages.home.presenters.HomePresenter;
import com.example.androidjava.allpages.home.presenters.HomePresenterImpl;
import com.example.androidjava.allpages.mealsList.views.OnMealClickListener;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements OnClickListener, HomeView, AuthView {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
private List<Category> categoryList = new ArrayList<>();
private List<Meal> areasList = new ArrayList<>();
View view;
ImageView signUpImg;
RecyclerView recyclerView;
AreaAdapter areaAdapter;
ImageView heartImg;
CardView randomMealCard;
Chip categotyChip;
Chip countryChip;
OnMealClickListener mealClickListener;
Repository repository;
private String mParam1;
private String mParam2;
Meal randomMeal = new Meal();
private HomePresenter homePresenter;
AuthPresenter authPresenter;

public Home() {

}


public static Home newInstance(String param1, String param2) {
	Home fragment = new Home();
	Bundle args = new Bundle();
	args.putString(ARG_PARAM1, param1);
	args.putString(ARG_PARAM2, param2);
	fragment.setArguments(args);
	return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	MealsRemoteDataSourceImpl remoteDataSource = new MealsRemoteDataSourceImpl();
	
	MealsLocalDataSourceImp localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	homePresenter = new HomePresenterImpl(this, repository);
	authPresenter = new AuthPresenterImpl(this, repository);
	if (getArguments() != null) {
		mParam1 = getArguments().getString(ARG_PARAM1);
		mParam2 = getArguments().getString(ARG_PARAM2);
	}
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_home, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	recyclerView = view.findViewById(R.id.recyclerView);
	categotyChip = view.findViewById(R.id.categoryChip);
	countryChip = view.findViewById(R.id.countryChip);
	randomMealCard = view.findViewById(R.id.includedMealCell);
	signUpImg =view.findViewById(R.id.leaveImg);
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
	homePresenter.getDailyInspration();
	homePresenter.showCategories();
	
	categotyChip.setOnClickListener(v -> homePresenter.showCategories());
	countryChip.setOnClickListener(v -> homePresenter.showCountries());
	signUpImg.setOnClickListener(v-> {authPresenter.signOut();});
	super.onViewCreated(view, savedInstanceState);
}

@Override
public void showCategories(List<Category> categories) {
	recyclerView.setAdapter(new CategoryAdapter(getContext(), categories, this));
}

@Override
public void showCountries(List<Meal> countries) {
	recyclerView.setAdapter(new AreaAdapter(getContext(), countries, this));
}

@Override
public void showRandomMeal(Meal meal) {
	if (isAdded()) {
		heartImg = randomMealCard.findViewById(R.id.heartImg);
		ImageView mealImage = randomMealCard.findViewById(R.id.itemImg);
		TextView mealName = randomMealCard.findViewById(R.id.Title);
		//TextView mealDesc = randomMealCard.findViewById(R.id.desc);
		String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
		
		Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
		mealName.setText(meal.getStrMeal());
		//mealDesc.setText(meal.getStrArea());
		heartImg.setOnClickListener(v -> {
			meal.setUserId(userId);
			homePresenter.addMealToFavorites(meal);
			Snackbar.make(view, "Meal added to favorites", Snackbar.LENGTH_SHORT)
					.show();
		
		});
		
		
		randomMealCard.setOnClickListener(v -> {
			Bundle bundle = new Bundle();
			if (meal == null || meal.getIdMeal() == null) {
				Toast.makeText(getContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
				return;
			}
			
			bundle.putString("name", meal.getIdMeal());
			NavController navController = NavHostFragment.findNavController(this);
			navController.navigate(R.id.action_home2_to_mealDetails, bundle);
			//	Navigation.findNavController(view).navigate(R.id.action_home2_to_mealDetails, bundle);
			
		});
	}
}

@Override
public void showError(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
			.show();
}

@Override
public void onCategoryListener(Category category) {
	Bundle bundle = new Bundle();
	bundle.putString("category", category.getStrCategory());
	NavController navController = NavHostFragment.findNavController(this);
	navController.navigate(R.id.action_home2_to_mealsList, bundle);
}


//@Override
//public void onFavClick(Meal favMeal) {
//
//}

@Override
public void onCountryClick(String country) {
	Bundle bundle = new Bundle();
	bundle.putString("country", country);
	Navigation.findNavController(view).navigate(R.id.action_home2_to_mealsList, bundle);
}


@Override
public void onAuthSuccess(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
			.show();
	Navigation.findNavController(view).navigate(R.id.action_home2_to_loginPage);
}

@Override
public void onAuthFailure(String error) {
	Snackbar.make(view, error, Snackbar.LENGTH_SHORT)
			.show();
}
}


