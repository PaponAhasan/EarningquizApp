package com.example.earningquizapp.dao

import com.example.earningquizapp.model.History
import com.example.earningquizapp.model.Question
import com.example.earningquizapp.model.Spin
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SpinDao {
    private val db = Firebase.database.reference
    private val spinData = db.child("SpinData")
    private val spinHistory = db.child("SpinHistory")
    private val currentUser = Firebase.auth.currentUser?.uid

    suspend fun getSpinData(): Spin {
        return withContext(Dispatchers.IO) {
            val snapshot: DataSnapshot = spinData.child(currentUser!!).get().await()
            snapshot.getValue(Spin::class.java) ?: throw NullPointerException("User data is null")
        }
    }

    suspend fun getSpinHistory(): History {
        return withContext(Dispatchers.IO) {
            val snapshot: DataSnapshot = spinHistory.child(currentUser!!).get().await()
            snapshot.getValue(History::class.java) ?: throw NullPointerException("User data is null")
        }
    }
}