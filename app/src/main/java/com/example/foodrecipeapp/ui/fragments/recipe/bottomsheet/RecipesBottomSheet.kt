package com.example.foodrecipeapp.ui.fragments.recipe.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.RecipesBottomSheetBinding
import com.example.foodrecipeapp.ui.MainActivity
import com.example.foodrecipeapp.util.Constants
import com.example.foodrecipeapp.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val recipesViewModel by lazy{
        (activity as MainActivity).recipesViewModel
    }
    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecipesBottomSheetBinding.inflate(layoutInflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener(object :
            ChipGroup.OnCheckedStateChangeListener {
            override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
                val chip = group.findViewById<Chip>(checkedIds.first())
                val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                mealTypeChip = selectedMealType
                mealTypeChipId = checkedIds.first()
            }

        })

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener(object :
            ChipGroup.OnCheckedStateChangeListener {
            override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
                val chip = group.findViewById<Chip>(checkedIds.first())
                val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
                dietTypeChip = selectedDietType
                dietTypeChipId = checkedIds.first()
            }
        }
        )

        binding.applyButton.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            recipesViewModel.isBackFromBottomSheet = true
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipeFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (exception: Exception) {
                Log.d("RecipeBottomSheet", exception.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}