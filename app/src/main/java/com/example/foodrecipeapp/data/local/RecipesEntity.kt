package com.example.foodrecipeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipeapp.models.FoodRecipe
import com.example.foodrecipeapp.util.Constants

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}