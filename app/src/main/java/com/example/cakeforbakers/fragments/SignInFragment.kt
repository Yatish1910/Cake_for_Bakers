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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cakeforbakers.R
import com.example.cakeforbakers.data.AuthViewModel
import com.example.cakeforbakers.data.Resource
import com.example.cakeforbakers.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSignInBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        binding.signInBt.setOnClickListener {
            val email = binding.emailIdEtSignIn.text.toString()
            val passWord = binding.passWordEtSignIn.text.toString()
            binding.signInPb.visibility = View.VISIBLE
            lifecycleScope.launch {
                viewModel.loginUser(email,passWord)
            }
            lifecycleScope.launch {
                viewModel.loginFlow.observe(viewLifecycleOwner, Observer {
                    when(it){
                        is Resource.Error -> {
                            Log.d("signInException",it.exception.message.toString())
                            Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                            binding.signInPb.visibility = View.GONE
                        }
                        Resource.Loading -> {binding.signInPb.visibility = View.VISIBLE}
                        is Resource.Success -> {
                            Log.d("signInResult",it.result.toString())
                            binding.signInPb.visibility = View.GONE
                            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        }
                        null -> {}
                    }
                })
            }
        }

    }

}