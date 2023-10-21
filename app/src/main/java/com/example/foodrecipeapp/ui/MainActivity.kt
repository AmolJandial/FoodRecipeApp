package com.example.foodrecipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.ActivityMainBinding
import com.example.foodrecipeapp.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var recipesViewModel: RecipesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipesViewModel = ViewModelProvider(this)[RecipesViewModel::class.java]
        navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.recipeFragment,
            R.id.favoriteRecipeFragment,
            R.id.jokesFragment
        ))
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}