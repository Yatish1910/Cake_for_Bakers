package com.example.cakeforbakers.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cakeforbakers.data.AuthViewModel
import com.example.cakeforbakers.data.Resource
import com.example.cakeforbakers.data.model.UserModel
import com.example.cakeforbakers.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

//    private fun setInitialUi() {
//        val firstName = binding.firstNameEt.text.toString()
//        val lastName = binding.secondNameEt.text.toString()
//        val emailId = binding.emailIdEt.text.toString()
//        val passWord = binding.passWordEt.text.toString()
//        val phoneNumber = binding.phoneNumberEt.text.toString()
//        val userModel = UserModel(firstName,lastName,emailId,phoneNumber)
//        Log.d("userModel",userModel.toString())
//
//    }

    private fun setUpUI() {
        binding.submitBt.setOnClickListener {

            lifecycleScope.launch {
                    binding.signUpPb.visibility = View.VISIBLE
                    val userModel = UserModel(
                        binding.firstNameEt.text.toString(),
                        binding.secondNameEt.text.toString(),
                        binding.emailIdEt.text.toString(),
                        binding.phoneNumberEt.text.toString()
                    )
                    Log.d("userDetailYatish", userModel.toString())
                    viewModel.signUpUser(userModel, "Yatish")
                    viewModel._signUpFlow.observe(viewLifecycleOwner, Observer {
                        when (it) {
                            is Resource.Error -> {
                                Toast.makeText(requireContext(), "Exception", Toast.LENGTH_SHORT)
                                    .show()
                                Log.d("SignUpError", it.exception.toString())
                                binding.signUpPb.visibility = View.INVISIBLE
                            }
                            Resource.Loading -> {
                                binding.signUpPb.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                                    .show()
                                binding.signUpPb.visibility = View.INVISIBLE
                            }
                            null -> {}
                        }
                    })

            }
        }


    }

}