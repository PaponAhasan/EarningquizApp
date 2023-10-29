package com.example.earningquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.databinding.ActivityMainBinding
import com.example.earningquizapp.viewmodel.CustomViewModelFactory
import com.example.earningquizapp.viewmodel.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val userViewModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(this))[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE

            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty() || age.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Required", Toast.LENGTH_LONG).show()
            } else {
                userViewModel.setUser(name, age, email, password)
                navigation()
            }
        }

        binding.tvSignin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            // Back is pressed... Finishing the activity
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            navigation()
        }
    }

    private fun navigation() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}