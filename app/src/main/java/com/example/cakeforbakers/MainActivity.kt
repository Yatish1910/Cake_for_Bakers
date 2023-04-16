package com.example.cakeforbakers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cakeforbakers.data.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}