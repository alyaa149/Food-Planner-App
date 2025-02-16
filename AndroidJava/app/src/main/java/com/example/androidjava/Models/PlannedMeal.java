package com.example.androidjava.Models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "planned_meals", primaryKeys = {"mealId", "userIdPlan", "day", "month", "year"})
public class PlannedMeal {

@Embedded
private Meal meal;

@NonNull
private int day;
@NonNull
private int month;
@NonNull
private int year;
@NonNull
private int mealId;
@NonNull
private String userIdPlan;

@NonNull
public String getUserIdPlan() {
	return userIdPlan;
}
public PlannedMeal() {
}
public PlannedMeal(Meal meal) {
	this.meal = meal;
}

public void setUserIdPlan(@NonNull String userIdPlan) {
	this.userIdPlan = userIdPlan;
}

public PlannedMeal(Meal meal,@NonNull int mealId, @NonNull String userIdPlan, @NonNull int day, @NonNull int month, @NonNull int year) {
	this.meal = meal;
    this.day = day;
    this.month = month;
    this.year = year;
	this.mealId = mealId;
	this.userIdPlan = userIdPlan;
}


public int getMealId() {
	return mealId;
}

public void setMealId(int mealId) {
	this.mealId = mealId;
}

public Meal getMeal() {
	return meal;
}

public void setMeal(Meal meal) {
	this.meal = meal;
}

@NonNull
public int getDay() {
	return day;
}

public void setDay(@NonNull int day) {
	this.day = day;
}

@NonNull
public int getMonth() {
	return month;
}

public void setMonth(@NonNull int month) {
	this.month = month;
}

@NonNull
public int getYear() {
	return year;
}

public void setYear(@NonNull int year) {
	this.year = year;
}

@NonNull
public String getUserId() {
	return userIdPlan;
}

public void setUserId(@NonNull String userId) {
	this.userIdPlan = userId;
}
}
