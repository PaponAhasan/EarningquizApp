package com.example.earningquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.databinding.ActivityLoginBinding
import com.example.earningquizapp.viewmodel.CustomViewModelFactory
import com.example.earningquizapp.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val userViewModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(this))[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Required", Toast.LENGTH_LONG).show()
            } else {
                if (email == "admin@652gmail.com" && password == "28517") {
                    startActivity(Intent(this, AddQuestionActivity::class.java))
                } else userViewModel.signInUser(email, password)
            }
        }

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            // Back is pressed... Finishing the activity
            finish()
        }
    }
}