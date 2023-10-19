package com.example.foodrecipeapp.ui.fragments.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.viewmodels.MainViewModel
import com.example.foodrecipeapp.adapters.RecipeAdapter
import com.example.foodrecipeapp.databinding.FragmentRecipeBinding
import com.example.foodrecipeapp.util.Constants
import com.example.foodrecipeapp.util.NetworkResult
import com.example.foodrecipeapp.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    lateinit var mainViewModel: MainViewModel
    lateinit var recipesViewModel: RecipesViewModel

    private val binding by lazy{
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy{
        RecipeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupRecyclerView()
        requireApiData()
        return binding.root
    }

    private fun requireApiData(){
        mainViewModel.getRecipes(recipesViewModel.applyQuery())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }

    }



    private fun setupRecyclerView(){
        binding.recipeRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}