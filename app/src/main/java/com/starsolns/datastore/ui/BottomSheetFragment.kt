package com.starsolns.datastore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.starsolns.datastore.R
import com.starsolns.datastore.databinding.FragmentBottomSheetBinding
import com.starsolns.datastore.viewmodel.MainViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private var genre: String = "Action"
    private var genreId: Int = 0
    private var country: String = "USA"
    private var countryId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)

        mainViewModel.readDatastore.asLiveData().observe(viewLifecycleOwner){data->
            genre = data.genre
            country = data.country
            updateChip(data.genreId, binding.genreChipGroup)
            updateChip(data.countryId, binding.countryChipGroup)

        }

        binding.genreChipGroup.setOnCheckedChangeListener { group, checkedGenreId ->
            val chip = group.findViewById<Chip>(checkedGenreId)
            val selectedGenre = chip.text.toString()
            genre = selectedGenre
            genreId = checkedGenreId
        }

        binding.countryChipGroup.setOnCheckedChangeListener { group, checkedCountryId ->
            val chip = group.findViewById<Chip>(checkedCountryId)
            val selectedCountry = chip.text.toString()
            country = selectedCountry
            countryId = checkedCountryId
        }

        binding.applyFiltersButton.setOnClickListener {
            mainViewModel.saveData(genre, genreId, country, countryId)
            findNavController().navigate(R.id.action_bottomSheetFragment_to_homeFragment)
        }

        return binding.root
    }

     private fun updateChip(chipId: Int, chip: ChipGroup) {
        if(chipId != 0){
            chip.findViewById<Chip>(chipId).isChecked = true
        }
    }
}