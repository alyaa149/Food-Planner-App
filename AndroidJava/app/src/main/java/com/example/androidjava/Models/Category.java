package com.example.androidjava.Models;

public class Category {
String strCategory;
int idCategory;
String strCategoryThumb;
String strCategoryDescription;

public Category(String strCategory, int idCategory, String strCategoryThumb, String strCategoryDescription) {
	this.strCategory = strCategory;
	this.idCategory = idCategory;
	this.strCategoryThumb = strCategoryThumb;
	this.strCategoryDescription = strCategoryDescription;
}

public String getStrCategory() {
	return strCategory;
}

public int getIdCategory() {
	return idCategory;
}

public String getStrCategoryThumb() {
	return strCategoryThumb;
}

public String getStrCategoryDescription() {
	return strCategoryDescription;
}

public void setStrCategory(String strCategory) {
	this.strCategory = strCategory;
}


public void setIdCategory(int idCategory) {
	this.idCategory = idCategory;
}

public void setStrCategoryThumb(String strCategoryThumb) {
	this.strCategoryThumb = strCategoryThumb;
}

public void setStrCategoryDescription(String strCategoryDescription) {
	this.strCategoryDescription = strCategoryDescription;
}
}
