package com.example.codechallenge.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.codechallenge.network.Resource
import com.example.codechallenge.util.visibile
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var bindig: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindig = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.show() // show toolbar
        (requireActivity() as MainActivity).binding.bottomNav.visibile(true) // show bottom nav



        bindig.edtSearch.setOnTouchListener { _, _ ->
            if (findNavController().currentDestination?.id == R.id.search) {
                (requireActivity() as MainActivity).navController.navigate(R.id.action_search_to_searchResultFragment)
                true
            }
            false
        }

    }

}