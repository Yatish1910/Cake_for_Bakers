package com.example.cakeforbakers.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.cakeforbakers.R
import com.example.cakeforbakers.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding =  FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
//        setUpBottomView()
        return binding.root
    }

//    private fun setUpBottomView() {
//        val navController = findNavController()
//        val bottomNavigationView = binding.homeFragmentBottomNavigation
////        bottomNavigationView.setOnItemSelectedListener {menuItem->
////            when(menuItem.itemId){
////                R.id.homeFragment ->{
////                    true
////                }
////                R.id.diningFragment -> {
////                    navController.navigate(R.id.action_homeFragment_to_diningFragment)
////                    true
////                }
////                else -> false
////            }
////        }
//        NavigationUI.setupWithNavController(bottomNavigationView,navController)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}