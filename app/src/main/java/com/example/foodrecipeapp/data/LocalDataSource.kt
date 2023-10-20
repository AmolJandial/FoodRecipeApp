package com.example.foodrecipeapp.data

import com.example.foodrecipeapp.data.local.RecipesDAO
import com.example.foodrecipeapp.data.local.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val recipesDAO: RecipesDAO
){
    suspend fun insertRecipes(recipesEntity: RecipesEntity) = recipesDAO.insertRecipes(recipesEntity)

    fun getRecipes(): Flow<List<RecipesEntity>> = recipesDAO.readRecipes()
}