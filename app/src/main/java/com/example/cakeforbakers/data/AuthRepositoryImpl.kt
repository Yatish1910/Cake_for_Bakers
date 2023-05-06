package com.example.cakeforbakers.data

import android.util.Log
import com.example.cakeforbakers.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(result.user!!)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun signUp(
        userModel: UserModel,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(userModel.email.toString(),password).await()
            Log.d("Devu",result.user!!.uid)
            firebaseFirestore.collection("UserInformation").document(result.user!!.uid)
                .set(userModel)
                .addOnCanceledListener { Log.d("additionalInfo",userModel.toString()) }
                .addOnFailureListener { Log.d("additionalInfo",it.message.toString()) }
            Resource.Success(result.user!!)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override fun signOut(): FirebaseUser? {
        firebaseAuth.signOut()
        return firebaseAuth.currentUser
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}