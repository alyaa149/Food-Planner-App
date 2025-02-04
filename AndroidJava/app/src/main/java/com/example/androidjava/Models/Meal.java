package com.example.androidjava.Models;

public class Meal {
private String idMeal;


private String strMealThumb;


private String strMeal;
private String strArea;

public Meal(String idMeal, String strMealThumb, String strMeal, String strArea) {
	this.idMeal = idMeal;
	this.strMealThumb = strMealThumb;
	this.strMeal = strMeal;
	this.strArea = strArea;
}
public String getStrArea() {
	return strArea;
}
public String getMealName() {
	return strMeal;
}

public String getMealImage() {
	return strMealThumb;
}
}
