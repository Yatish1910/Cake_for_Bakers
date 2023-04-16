package com.example.cakeforbakers.data

import com.example.cakeforbakers.data.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email:String,password:String):Resource<FirebaseUser>

    suspend fun signUp(userModel: UserModel,password:String):Resource<FirebaseUser>

    fun signOut() : FirebaseUser?

    fun getCurrentUser() : FirebaseUser?
}