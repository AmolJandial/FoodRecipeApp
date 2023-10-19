package com.example.foodrecipeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodrecipeapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application){

    fun applyQuery(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_NUMBER] = "50"
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = "snack"
        queries[Constants.QUERY_DIET] = "vegan"
        queries[Constants.QUERY_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries

    }

}