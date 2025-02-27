package com.example.androidjava.allpages.mealDetails.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidjava.Models.Meal;
import com.example.androidjava.Models.PlannedMeal;
import com.example.androidjava.Models.RepositoryImpl;
import com.example.androidjava.R;
//import com.example.androidjava.adapters.StepAdapter;
import com.example.androidjava.alldata.localdata.MealsLocalDataSourceImp;
import com.example.androidjava.allpages.mealDetails.presenters.MealDetailsPresenterImpl;
import com.example.androidjava.network.MealsRemoteDataSourceImpl;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetails extends Fragment implements MealDetailsView {


private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

int dayPlan;
int monthPlan;
int yearPlan;
private String mParam1;
private String mParam2;
WebView webView;
RecyclerView recyclerView;

IngrediantAdapter ingrediantAdapter;


Meal meal;
String mealId;
private MealDetailsPresenterImpl mealDetailsPresenterImpl ;
RepositoryImpl repository;

TextView mealArea, mealName, steps;
ImageView mealImg, planSaveImg;
View view;
List<Meal> meals = new ArrayList<>();
Map<String, String> ingredientsDetails = new HashMap<>();

public MealDetails() {

}


public static MealDetails newInstance(String param1, String param2) {
	MealDetails fragment = new MealDetails();
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
	mealDetailsPresenterImpl = new MealDetailsPresenterImpl(repository, this);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_meal_details, container, false);
	return view;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	getTheMealFromArguments();
	init();
	mealDetailsPresenterImpl.getMealDetails(Integer.parseInt(mealId));
	if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
		planSaveImg.setOnClickListener(v -> showPlanDaysDialog(meal));
	}
}

public void init() {
	recyclerView = view.findViewById(R.id.recyclerView);
	//stepRecyclerView=view.findViewById(R.id.stepsRecyclerView);
	mealName = view.findViewById(R.id.mealName);
	mealArea = view.findViewById(R.id.country);
	mealImg = view.findViewById(R.id.mealImage);
	steps = view.findViewById(R.id.steps);
	webView = view.findViewById(R.id.mealVideo);
	planSaveImg = view.findViewById(R.id.planSaveImg);
}


private void showIngredients() {
	Map<String, String> ingredientsDetails = new HashMap<>();
	
	try {
		for (int i = 1; i <= 20; i++) {
			Field ingredientField = Meal.class.getDeclaredField("strIngredient" + i);
			ingredientField.setAccessible(true);
			String ingredient = (String) ingredientField.get(meal);
			String thumbnailUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
			if (ingredient != null && !ingredient.isEmpty() && thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
				ingredientsDetails.put(ingredient, thumbnailUrl);
			}
		}
		if (!ingredientsDetails.isEmpty()) {
			ingrediantAdapter = new IngrediantAdapter(getContext(), ingredientsDetails);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
			recyclerView.setAdapter(ingrediantAdapter);
			ingrediantAdapter.notifyDataSetChanged();
		}
	} catch (Exception e) {
		e.printStackTrace(); // Handle reflection errors
	}
}
private void showPlanDaysDialog(Meal meal) {

	Calendar calendar = Calendar.getInstance();
	DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
			R.style.RedDatePickerDialog,
			(view, year, month, dayOfMonth) -> {},
			calendar.get(Calendar.YEAR),
			calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH)
	);
	datePickerDialog.setOnShowListener(dialog -> {
		Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
		Button negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE);
		
		positiveButton.setTextColor(Color.RED);
		negativeButton.setTextColor(Color.BLACK);
		
		positiveButton.setOnClickListener(v -> {
			dayPlan = datePickerDialog.getDatePicker().getDayOfMonth();
			monthPlan = datePickerDialog.getDatePicker().getMonth() + 1;
			yearPlan = datePickerDialog.getDatePicker().getYear();
		    mealDetailsPresenterImpl.insertPlannedMealFireBase(dayPlan, monthPlan, yearPlan, meal);
		});
	});

	
	datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
	datePickerDialog.show();
}


private void updateUI() {
	if (meal != null) {
		mealName.setText(meal.getStrMeal());
		mealArea.setText("." + meal.getStrArea());
		steps.setText(meal.getStrInstructions());
		if (isAdded()) {
			Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImg);
		}
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		String url = meal.getStrYoutube();
		String videoId = "";
		
		if (url != null && url.contains("v=")) {
			String[] parts = url.split("v="); //ht   v=
			if (parts.length > 1) {
				videoId = parts[1].split("&")[0];
			}
		}
		
		if (!videoId.isEmpty()) {
			String embedUrl = "https://www.youtube.com/embed/" + videoId;
			webView.loadUrl(embedUrl);
		} else {
			Log.e("YouTubeEmbed", "Invalid YouTube URL: " + url);
		}
		
//		String videoId = meal.getStrYoutube().split("v=")[1];
//		String embedUrl = "https://www.youtube.com/embed/" + videoId;
//		webView.loadUrl(embedUrl);
	
	}
}

private void getTheMealFromArguments() {
	Bundle bundle = getArguments();
	if (bundle != null) {
		mealId = (String) bundle.getString("name");
	} else {
		Toast.makeText(getContext(), "Bundle is null", Toast.LENGTH_SHORT).show();
	}
}


@Override
public void showMealDetails(Meal meal) {
	this.meal = meal;
	updateUI();
	showIngredients();
//	ingrediantAdapter.notifyDataSetChanged();
}



@Override
public void showError(String message) {
	Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
}

@Override
public void showSuccess(String message) {
	Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
	
}


}