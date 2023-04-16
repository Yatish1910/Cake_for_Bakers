package com.example.cakeforbakers.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cakeforbakers.data.model.UserModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
):ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    val _signUpFlow = MutableLiveData<Resource<FirebaseUser>?>(null)
//    val signUpFlow:LiveData<Resource<FirebaseUser>?> = _signUpFlow


    val currentUser:FirebaseUser?
        get() = repository.getCurrentUser()

    init {
        if(repository.getCurrentUser()!=null){
            _loginFlow.value = Resource.Success(repository.getCurrentUser()!!)
        }
    }

    fun loginUser(email:String, password:String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email,password)
        _loginFlow.value  = result
    }

    fun signUpUser(userModel: UserModel,password: String) = viewModelScope.launch {
        Log.d("userDetailYatish",userModel.toString())
        _signUpFlow.value = Resource.Loading
        val result = repository.signUp(userModel,password)
        _signUpFlow.value = result
    }

    fun logOut(){
        repository.signOut()
    }

}