package com.example.earningquizapp.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earningquizapp.DashboardActivity
import com.example.earningquizapp.dao.UserDao
import com.example.earningquizapp.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import kotlinx.coroutines.launch

class UserViewModel(private val context: Context) : ViewModel() {

    private val currentUser = Firebase.auth.currentUser?.uid
    private val db = Firebase.database.reference
    private val userCollection = db.child("Users")
    private val auth = Firebase.auth

    val userInfoResponse: MutableLiveData<User?> = MutableLiveData()

    fun setUser(name: String, age: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // store user data in firebase
                    viewModelScope.launch {
                        val user = User(name, age.toInt(), email, password)
                        val userDao = UserDao()
                        userDao.setUser(user)
                    }

                } else {
                    Toast.makeText(
                        context,
                        it.exception?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun signInUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                context.startActivity(Intent(context, DashboardActivity::class.java))
            }else{
                Toast.makeText(
                    context,
                    it.exception?.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val userDao = UserDao()
            val user = userDao.getUser()
            userInfoResponse.value = user
        }

        // OR
        /*if (currentUser != null) {
            userCollection.child(currentUser).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        userInfoResponse.value = user
                    }

                    override fun onCancelled(error: DatabaseError) {}
                }
            )
        }*/
    }
}