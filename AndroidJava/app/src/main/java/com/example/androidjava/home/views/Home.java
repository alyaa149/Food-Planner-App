package com.example.androidjava.home.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.CategoryResponse;
import com.example.androidjava.Models.FavMeal;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.MealRepository;
import com.example.androidjava.Models.MealRepositoryImpl;
import com.example.androidjava.Models.MealResponse;
import com.example.androidjava.R;
import com.example.androidjava.db.localdata.MealsLocalDataSource;
import com.example.androidjava.home.presenters.HomePresenter;
import com.example.androidjava.home.presenters.HomePresenterImpl;
import com.example.androidjava.listeners.OnMealClickListener;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements OnClickListener,HomeView {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";
private List<Category> categoryList = new ArrayList<>();
private List<Meal> areasList = new ArrayList<>();
View view;
RecyclerView recyclerView;
AreaAdapter areaAdapter;
CardView randomMealCard;
Chip categotyChip;
Chip countryChip;
OnMealClickListener mealClickListener;
MealRepository repository;
private String mParam1;
private String mParam2;
Meal randomMeal = new Meal();
private HomePresenter homePresenter;

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
	
	//MealsLocalDataSource localDataSource = MealsLocalDataSource.getInstance(this);
	//repository = new MealRepositoryImpl(remoteDataSource, localDataSource);
	repository = new MealRepositoryImpl(remoteDataSource);
	homePresenter = new HomePresenterImpl(this, repository);
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
	
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
	homePresenter.getDailyInspration();
	homePresenter.showCategories();
	
	categotyChip.setOnClickListener(v -> homePresenter.showCategories());
	countryChip.setOnClickListener(v -> homePresenter.showCountries());
	
	
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
	ImageView mealImage = randomMealCard.findViewById(R.id.itemImg);
	TextView mealName = randomMealCard.findViewById(R.id.Title);
	TextView mealDesc = randomMealCard.findViewById(R.id.desc);
	
	Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
	mealName.setText(meal.getStrMeal());
	mealDesc.setText(meal.getStrArea());
	randomMealCard.setOnClickListener(v-> {
		Bundle bundle = new Bundle();
		if (meal == null || meal.getIdMeal() == null) {
			Toast.makeText(getContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
			return;
		}
		
		bundle.putString("name", meal.getIdMeal());
		Navigation.findNavController(view).navigate(R.id.action_home2_to_mealDetails, bundle);
		
	});
}

@Override
public void showError(String message) {
	Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
}

@Override
public void onCategoryListener(Category category) {
	Bundle bundle = new Bundle();
	bundle.putString("category", category.getStrCategory());
	Navigation.findNavController(view).navigate(R.id.action_home2_to_mealsList, bundle);
	
}

@Override
public void onFavClick(FavMeal favMeal) {

}

@Override
public void onCountryClick(String country) {
	Bundle bundle = new Bundle();
	bundle.putString("country", country);
	Navigation.findNavController(view).navigate(R.id.action_home2_to_mealsList, bundle);
}
}


