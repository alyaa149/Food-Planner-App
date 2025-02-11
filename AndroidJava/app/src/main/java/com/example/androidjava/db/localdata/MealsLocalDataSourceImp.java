//package com.example.androidjava.db.localdata;
//
//import android.content.Context;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.androidjava.Models.FavMeal;
//
//import java.util.List;
//
//public class MealsLocalDataSourceImp {
//private PlanDAO dao;
//private FavDAO favDao;
//private static MealsLocalDataSourceImp localDataSource=null;
//LiveData<List<FavMeal>> storedFavMeals;
//LiveData<List<FavDAO>> storedFavProducts;
//
//private MealsLocalDataSourceImp(Context context){
//	AppDataBase db =AppDataBase.getInstanse(context.getApplicationContext());
////	dao = db.getProductDao();
////	storedProducts= dao.getAllProducts();
//
//}
//
//public static MealsLocalDataSourceImp getInstance(Context context){
//	if(localDataSource==null){
//		localDataSource = new MealsLocalDataSourceImp(context);
//	}
////	return localDataSource;
//}
//}
