package com.example.foodrecipeapp.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipeapp.data.local.RecipesEntity
import com.example.foodrecipeapp.models.FoodRecipe
import com.example.foodrecipeapp.util.NetworkResult

class RecipesBinding {

    companion object{
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(view: ImageView,
                                     apiResponse: NetworkResult<FoodRecipe?>?,
                                     database: List<RecipesEntity>?
                                     ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                view.visibility = View.VISIBLE
            }else if(apiResponse is NetworkResult.Loading){
                view.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(view: TextView,
                                    apiResponse: NetworkResult<FoodRecipe>?,
                                    database: List<RecipesEntity>?){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                view.visibility = View.VISIBLE
                view.text = apiResponse.message.toString()
            }else if(apiResponse is NetworkResult.Loading){
                view.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }

    }

}