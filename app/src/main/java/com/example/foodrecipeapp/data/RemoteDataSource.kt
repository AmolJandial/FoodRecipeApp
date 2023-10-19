package com.example.foodrecipeapp.data

import com.example.foodrecipeapp.data.remote.FoodRecipeApi
import com.example.foodrecipeapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        foodRecipeApi.getRecipes(queries)
}