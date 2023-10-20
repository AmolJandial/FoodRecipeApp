package com.example.foodrecipeapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodrecipeapp.data.local.RecipesDatabase
import com.example.foodrecipeapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRecipeDao(database: RecipesDatabase) = database.recipesDao()

}