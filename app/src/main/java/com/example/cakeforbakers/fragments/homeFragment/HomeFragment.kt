package com.example.cakeforbakers.fragments.homeFragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cakeforbakers.databinding.FragmentHomeBinding
import com.example.cakeforbakers.fragments.homeFragment.adapter.MoviePagingAdapter
import com.example.cakeforbakers.fragments.homeFragment.adapter.PagingLoadingAdapter
import com.example.cakeforbakers.viewModel.MovieViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentHomeBinding
    private val movieViewModel : MovieViewModel by viewModels()
    private lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchLocation()
        setUpUI()
        getData()
    }

    private fun setUpUI() {
        binding.movieListRv.layoutManager = LinearLayoutManager(requireContext())
        binding.movieListRv.setHasFixedSize(true)
        moviePagingAdapter = MoviePagingAdapter()
        binding.movieListRv.adapter = moviePagingAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadingAdapter(),
            footer = PagingLoadingAdapter()
        )
    }

    private fun getData() {
        lifecycleScope.launch {
            movieViewModel.getAllMovies.observe(viewLifecycleOwner, Observer {
                moviePagingAdapter.submitData(lifecycle,it)
            })

        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun fetchLocation() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Log.d("locationYatish", location.toString())
                                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                                    try {
                                        val addresses = geocoder.getFromLocation(
                                            location.latitude,
                                            location.longitude,
                                            1
                                        )
                                        if (addresses != null && addresses.size > 0) {
                                            val address = addresses[0]
                                            val currentAddress = address.getAddressLine(0)
                                            // Use currentAddress here
                                            Log.d("currentA", currentAddress)
                                        }
                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }
                                }
                            }

                    }


                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }

                else -> {
                    // No location access granted.
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }




}