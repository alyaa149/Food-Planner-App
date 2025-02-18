package com.example.androidjava.allpages.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.androidjava.Models.Category;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
import com.example.androidjava.Utils.NetworkUtils;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.home.presenters.HomePresenterImpl;
import com.example.androidjava.allpages.mealsList.presenters.MealsListPresenter;
import com.example.androidjava.allpages.mealsList.views.MealAdapter;
import com.example.androidjava.allpages.mealsList.views.MealsList;
import com.example.androidjava.allpages.mealsList.views.OnMealClickListener;
import com.example.androidjava.allpages.search.presenter.SearchPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView , OnMealClickListener {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";


private String mParam1;
private String mParam2;
RepositoryImpl repository;
LottieAnimationView searchAnimation;
SearchPresenterImpl searchPresenter;
RecyclerView recyclerView;
MealAdapter mealAdapter;
CardView filterCard;
View view;
List<Meal> mealList =new ArrayList<>();
TextInputLayout searchInputLayout;
boolean visible =false;
EditText searchEditText;
TextView searchByCategoryTV,searchByCountryTV,searchByIngredientTV;


public SearchFragment() {
}


public static SearchFragment newInstance(String param1, String param2) {
	SearchFragment fragment = new SearchFragment();
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
	
	MealsRemoteDataSourceImpl remoteDataSource = new MealsRemoteDataSourceImpl();
	
	MealsLocalDataSourceImp localDataSource = MealsLocalDataSourceImp.getInstance(getContext());
	repository = new RepositoryImpl(remoteDataSource, localDataSource);
	searchPresenter = new SearchPresenterImpl(this, repository);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_search, container, false);
//	TextInputLayout searchInputLayout = view.findViewById(R.id.searchInputLayout);
//	searchInputLayout.setEndIconOnClickListener(v -> {
//		//SearchInput();
//	});
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	
	super.onViewCreated(view, savedInstanceState);
	this.view = view;

	mealList.clear();
	recyclerView = view.findViewById(R.id.searchrecyclerView);
	
	mealAdapter = new MealAdapter(getContext(), mealList, this);
	//recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
	recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	recyclerView.setAdapter(mealAdapter);

	
	searchInputLayout = view.findViewById(R.id.searchInputLayout);
	filterCard = view.findViewById(R.id.filterCard);
	searchByCountryTV=view.findViewById(R.id.searchByCountriesTV);
	searchByCategoryTV=view.findViewById(R.id.searchByCategoryTV);
	searchByIngredientTV=view.findViewById(R.id.searchByCategoryIngredientTV);
	searchEditText = searchInputLayout.getEditText();
	 searchAnimation=view.findViewById(R.id.searchAnimation);
	searchInputLayout.setEndIconOnClickListener(v -> {
		if(visible){
			filterCard.setVisibility(View.GONE);
			visible=false;
		}else{
			filterCard.setVisibility(View.VISIBLE);
			visible=true;
		}
		
		
	});
	filterMeal();
	searchByCountryTV.setOnClickListener(v->{searchInput("country");filterCard.setVisibility(View.GONE);});
	searchByCategoryTV.setOnClickListener(v->{searchInput("category");filterCard.setVisibility(View.GONE);});
	searchByIngredientTV.setOnClickListener(v->{searchInput("ingredient");filterCard.setVisibility(View.GONE);});
	

}
private void searchInput(String type) {
	searchAnimation.setVisibility(View.GONE);
	searchEditText.setOnEditorActionListener((v, actionId, event) -> {
	
		if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			String query = searchEditText.getText().toString().trim();
			Log.i("DEBUG", "Search triggered for: " + query);
			
			switch (type) {
				case "category":
					Log.i("DEBUG", "Calling searchPresenter.searchCategories() for: " + query);
					searchPresenter.searchCategories(query);
					break;
				case "country":
					Log.i("DEBUG", "Calling searchPresenter.searchCountries() for: " + query);
					searchPresenter.searchCountries(query);
					break;
				case "ingredient":
					Log.i("DEBUG", "Calling searchPresenter.searchIngredients() for: " + query);
					searchPresenter.searchIngredients(query);
					break;
				default:
					break;
			}
			return true;
		}
		return false;
	});
}

private void filterMeal() {
	
	
	if (searchEditText != null) {
		
		searchEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				searchAnimation.setVisibility(View.GONE);
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("DEBUG", "searchInput: " + s.toString());
							searchPresenter.searchMeals(s.toString());
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			
			}
			
			
		});
	}
	
}
@Override
public void onMealClick(Meal meal) {
	if (meal == null || meal.getIdMeal() == null) {
		Log.i("DEBUG", "Meal is null or id is null");
		return;
	}
	
	Bundle bundle = new Bundle();
	bundle.putString("name", meal.getIdMeal());
	
	NavController navController = Navigation.findNavController(view);
	navController.navigate(R.id.action_searchFragment_to_mealDetails, bundle);
}

@Override
public void onFavClick(Meal meal) {
	if (isAdded()) {
		Toast.makeText(getContext(), "Meal click ->" +meal.getIdMeal(), Toast.LENGTH_SHORT).show();
		searchPresenter.addMealToFavorites(meal);
		Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_favoritesPage);
	}
}

@Override
public void showMeals(List<Meal> meals) {
	if(isAdded()) {
		 //Log.d("DEBUG", "Meals received: " + meals.size());
//		Toast.makeText(getContext(), "Meals received: " + meals.size(), Toast.LENGTH_SHORT).show();
		
		if (meals == null || meals.isEmpty()) {
			mealList.clear();
			Toast.makeText(getContext(), "No meals received!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mealList.clear();
		mealList.addAll(meals);
		mealAdapter.notifyDataSetChanged();
		

		
		Log.d("DEBUG", "RecyclerView is visible");
	}
}

@Override
public void showError(String errorMessage) {
    Log.i("DEBUG", "Error message: " + errorMessage);

}
}