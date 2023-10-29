package com.example.earningquizapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earningquizapp.dao.CategoryDao
import com.example.earningquizapp.model.Question
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    var questionResponse: MutableLiveData<List<Question>> = MutableLiveData()

    fun getQuestion(categoryTitle: String) {

        viewModelScope.launch {
            val dao = CategoryDao()
            questionResponse.value = dao.getQuestion(categoryTitle)
        }

//        val questionList: MutableList<Question> = mutableListOf()
//
//        Firebase.firestore.collection("Subjects")
//            .document(categoryTitle)
//            .collection("Quizs")
//            .get().addOnSuccessListener { question ->
//                questionList.clear()
//                for (data in question.documents) {
//                    val question = data.toObject(Question::class.java)
//                    questionList.add(question!!)
//                }
//                questionResponse.value = questionList
//            }
    }

    fun setQuestion(categoryTitle: String, dataQ: Question) {
        viewModelScope.launch {
            val dao = CategoryDao()
            dao.setQuestion(categoryTitle, dataQ)
        }

        /*
        Firebase.firestore.collection("Subjects").document(subject).collection("Quizs")
            .add(dataQ).addOnSuccessListener {
                Toast.makeText(
                    this@AddQuestionActivity,
                    "Question Added",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                Toast.makeText(
                    this@AddQuestionActivity,
                    "Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
    }
}