package com.example.earningquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.databinding.ActivityAddQuestionBinding
import com.example.earningquizapp.model.Question
import com.example.earningquizapp.viewmodel.CategoryViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AddQuestionActivity : AppCompatActivity() {
    private val questionBinding: ActivityAddQuestionBinding by lazy {
        ActivityAddQuestionBinding.inflate(layoutInflater)
    }

    private val questionViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(questionBinding.root)

        var subject = ""

        questionBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    subject = adapterView?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        questionBinding.btnAddQuestion.setOnClickListener {
            val question = questionBinding.etQuestion.text.toString().trim()
            val option1 = questionBinding.etOption1.text.toString().trim()
            val option2 = questionBinding.etOption2.text.toString().trim()
            val option3 = questionBinding.etOption3.text.toString().trim()
            val option4 = questionBinding.etOption4.text.toString().trim()
            val answer = questionBinding.etAnswer.text.toString().trim()

            if (question.isEmpty() || option1.isEmpty() || option2.isEmpty()
                || option3.isEmpty() || option4.isEmpty() || answer.isEmpty()
            ) {
                Toast.makeText(
                    this@AddQuestionActivity,
                    "please, select option",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val dataQ = Question(answer, option1, option2, option3, option4, question)
                questionViewModel.setQuestion(subject, dataQ)
                Toast.makeText(
                    this@AddQuestionActivity,
                    "Question Added",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            // Back is pressed... Finishing the activity
            finish()
        }
    }
}