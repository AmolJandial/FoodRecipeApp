package com.example.foodrecipeapp.data.local

import androidx.room.TypeConverter
import com.example.foodrecipeapp.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String =
        gson.toJson(foodRecipe)

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listype = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listype)
    }

}