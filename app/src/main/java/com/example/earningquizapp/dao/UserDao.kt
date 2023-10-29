package com.example.earningquizapp.dao

import com.example.earningquizapp.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserDao {
    private val db = Firebase.database.reference
    private val userCollection = db.child("Users")

    private val currentUser = Firebase.auth.currentUser?.uid

    suspend fun setUser(user: User) {
        withContext(Dispatchers.IO) {
            userCollection.child(currentUser!!)
                .setValue(user)
        }
    }

    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            val snapshot: DataSnapshot = userCollection.child(currentUser!!).get().await()
            snapshot.getValue(User::class.java) ?: throw NullPointerException("User data is null")
            /*val snapshot1: DataSnapshot = Tasks.await(userCollection.child(currentUser!!).get())
            snapshot1.getValue(User::class.java)!! */
        }
    }
}