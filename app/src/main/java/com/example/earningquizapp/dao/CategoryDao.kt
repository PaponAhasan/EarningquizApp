package com.example.earningquizapp.dao

import com.example.earningquizapp.model.Question
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CategoryDao {
    private val db = Firebase.firestore
    private val categoryCollection = db.collection("Subjects")

    suspend fun getQuestion(categoryTitle: String): List<Question> {
        return withContext(Dispatchers.IO) {
            val snapshot =
                categoryCollection.document(categoryTitle).collection("Quizs").get().await()
            snapshot.documents.mapNotNull { it.toObject(Question::class.java) }
        }
    }

    suspend fun setQuestion(categoryTitle: String, dataQ: Question) {
        withContext(Dispatchers.IO) {
            categoryCollection.document(categoryTitle).collection("Quizs").add(dataQ).await()
        }
    }
}