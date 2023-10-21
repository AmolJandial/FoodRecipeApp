package com.example.foodrecipeapp.util

object Constants {
    const val BASE_URL = "https://api.spoonacular.com"
    const val API_KEY = "8c925752b7854ecdabbb32d9cac8a5c9"

    //Query Parameters
    const val QUERY_NUMBER = "number"
    const val QUERY_TYPE = "type"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_DIET = "diet"
    const val QUERY_RECIPE_INFORMATION = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"


    //Room Database
    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"

    //Bottom Sheet
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val DEFAULT_RECIPE_NUMBER = "50"
    const val PREFERENCES_NAME = "food preferences"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCE_DIET_TYPE = "dietType"
    const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCE_BACK_ONLINE = "backOnline"

}