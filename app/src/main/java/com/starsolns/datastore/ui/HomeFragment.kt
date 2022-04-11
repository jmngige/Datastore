package com.starsolns.datastore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.starsolns.datastore.databinding.FragmentHomeBinding
import com.starsolns.datastore.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mainViewModel.readDatastore.asLiveData().observe(viewLifecycleOwner){data->
            binding.movieGenre.text = data.genre
            binding.movieCountry.text = data.country
        }

        binding.filterMoviesFab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}