package com.example.earningquizapp.viewmodel

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earningquizapp.dao.SpinDao
import com.example.earningquizapp.model.History
import com.example.earningquizapp.model.Question
import com.example.earningquizapp.model.Spin
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch


class SpinViewModel : ViewModel() {
    var spinHistoryResponse: MutableLiveData<MutableList<History>> = MutableLiveData()
    val spinInfoResponse: MutableLiveData<Spin?> = MutableLiveData()

    fun setSpinData(spinData: Spin) {
        Firebase.database.reference.child("SpinData").child(Firebase.auth.currentUser!!.uid)
            .setValue(spinData)
    }

    fun getSpinData() {
        viewModelScope.launch {
            val spinDao = SpinDao()
            val spin = spinDao.getSpinData()
            spinInfoResponse.value = spin
        }
    }

    fun putSpinHistory(spinHistory: History) {
        Firebase.database.reference.child("SpinHistory").child(Firebase.auth.currentUser!!.uid)
            .push().setValue(spinHistory)
    }

    fun getSpinHistory() {

        val databaseReference =
            Firebase.database.reference.child("SpinHistory")
                .child(Firebase.auth.currentUser!!.uid)

        val history: MutableList<History> = mutableListOf()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                history.clear()
                for (snapshot in snapshot.children) {
                    val his = snapshot.getValue(History::class.java)
                    if (his != null) {
                        history.add(his)
                    }
                }
                spinHistoryResponse.value = history
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}