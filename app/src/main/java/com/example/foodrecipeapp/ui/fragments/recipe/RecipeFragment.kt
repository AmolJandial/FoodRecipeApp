package com.example.foodrecipeapp.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.viewmodels.MainViewModel
import com.example.foodrecipeapp.adapters.RecipeAdapter
import com.example.foodrecipeapp.databinding.FragmentRecipeBinding
import com.example.foodrecipeapp.ui.MainActivity
import com.example.foodrecipeapp.util.Constants
import com.example.foodrecipeapp.util.NetworkListener
import com.example.foodrecipeapp.util.NetworkResult
import com.example.foodrecipeapp.util.observeOnce
import com.example.foodrecipeapp.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val recipesViewModel by lazy{
        (activity as MainActivity).recipesViewModel
    }

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy{
        RecipeAdapter()
    }

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel



        setupRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                Log.d("network listener", "onCreateView: $status")
                recipesViewModel.networkStatus = status
                recipesViewModel.showNetworkStatus()
                readDatabase()
            }
        }

        binding.fab.setOnClickListener {
            if(recipesViewModel.networkStatus){
                findNavController().navigate(R.id.action_recipeFragment_to_recipesBottomSheet)

            }else{
                recipesViewModel.showNetworkStatus()
            }
        }
        return binding.root
    }

    private fun readDatabase(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner){ database ->
                if(database.isNotEmpty() && !recipesViewModel.isBackFromBottomSheet){
                    mAdapter.setData(database[0].foodRecipe)
                }else{
                    requireApiData()
                }
            }
        }
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
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }

    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){ database ->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}