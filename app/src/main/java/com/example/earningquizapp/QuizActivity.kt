package com.example.earningquizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.databinding.ActivityQuizBinding
import com.example.earningquizapp.model.Question
import com.example.earningquizapp.model.Spin
import com.example.earningquizapp.viewmodel.CategoryViewModel
import com.example.earningquizapp.viewmodel.CustomViewModelFactory
import com.example.earningquizapp.viewmodel.SpinViewModel
import com.example.earningquizapp.viewmodel.UserViewModel

class QuizActivity : AppCompatActivity() {

    private val binding: ActivityQuizBinding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }

    private val userViewModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(this))[UserViewModel::class.java]
    }

    private val questionViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    private val spinViewModel by lazy {
        ViewModelProvider(this)[SpinViewModel::class.java]
    }

    private var questionScore = 0
    private var spinChance = 0
    private var selectOption = ""
    private var totalCoin = ""
    private var totalChange = ""

    private var questionList: MutableList<Question> = mutableListOf()

    private var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnQuestion.isEnabled = false

        val categoryImg = intent.getIntExtra("categoryImg", 0)
        val categoryTitle = intent.getStringExtra("categoryTitle")

        if (categoryImg != 0 && !categoryTitle.isNullOrEmpty()) {
            binding.ivCatImge.setImageResource(categoryImg)

            questionViewModel.getQuestion(categoryTitle)
            questionViewModel.questionResponse.observe(this) {
                for (data in it.listIterator()) {
                    questionList.add(data)
                }
                nextQuestion()
            }
            selectOption()
        }

        userViewModel.getUser()
        userViewModel.userInfoResponse.observe(this) { user ->
            binding.tvBrName.text = user?.name
        }

        spinViewModel.getSpinData()
        spinViewModel.spinInfoResponse.observe(this) { spin ->
            totalCoin = spin?.coin.toString()
            totalChange = spin?.chance.toString()
            binding.tvBrCoinAmount.text = totalCoin
        }
    }

    private fun selectOption() {
        binding.btnOption1.setOnClickListener {
            binding.btnQuestion.isEnabled = true
            selectOption = binding.btnOption1.text.toString().trim().replace(" ", "")

            binding.btnOption1.setBackgroundColor(Color.GREEN)
            binding.btnOption2.setBackgroundColor(Color.WHITE)
            binding.btnOption3.setBackgroundColor(Color.WHITE)
            binding.btnOption4.setBackgroundColor(Color.WHITE)
        }
        binding.btnOption2.setOnClickListener {
            binding.btnQuestion.isEnabled = true
            selectOption = binding.btnOption2.text.toString().trim().replace(" ", "")

            binding.btnOption1.setBackgroundColor(Color.WHITE)
            binding.btnOption2.setBackgroundColor(Color.GREEN)
            binding.btnOption3.setBackgroundColor(Color.WHITE)
            binding.btnOption4.setBackgroundColor(Color.WHITE)
        }
        binding.btnOption3.setOnClickListener {
            binding.btnQuestion.isEnabled = true
            selectOption = binding.btnOption3.text.toString().trim().replace(" ", "")

            binding.btnOption1.setBackgroundColor(Color.WHITE)
            binding.btnOption2.setBackgroundColor(Color.WHITE)
            binding.btnOption3.setBackgroundColor(Color.GREEN)
            binding.btnOption4.setBackgroundColor(Color.WHITE)
        }
        binding.btnOption4.setOnClickListener {
            binding.btnQuestion.isEnabled = true
            selectOption = binding.btnOption4.text.toString().trim().replace(" ", "")

            binding.btnOption1.setBackgroundColor(Color.WHITE)
            binding.btnOption2.setBackgroundColor(Color.WHITE)
            binding.btnOption3.setBackgroundColor(Color.WHITE)
            binding.btnOption4.setBackgroundColor(Color.GREEN)
        }
        binding.btnQuestion.setOnClickListener {
            if (isValidQuestion(selectOption)) {
                nextQuestion()
            }
        }
    }

    private fun nextQuestion() {
        if (questionList.size == 0) {
            // empty question list
            Toast.makeText(this, "No Question Found", Toast.LENGTH_SHORT).show()
        } else if (currentQuestion >= questionList.size) {
            // you are win
            Toast.makeText(
                this,
                "you win $questionScore coin & get spin change",
                Toast.LENGTH_SHORT
            ).show()
            binding.viewWinner.visibility = View.VISIBLE
            binding.layoutQuiz.visibility = View.GONE
            navigation()
        } else {
            binding.tvQuestion.text = questionList[currentQuestion].question
            binding.btnOption1.text = questionList[currentQuestion].option1
            binding.btnOption2.text = questionList[currentQuestion].option2
            binding.btnOption3.text = questionList[currentQuestion].option3
            binding.btnOption4.text = questionList[currentQuestion].option4

            binding.btnQuestion.isEnabled = false
            binding.pvQuestion.visibility = View.GONE

            binding.btnOption1.setBackgroundColor(Color.WHITE)
            binding.btnOption2.setBackgroundColor(Color.WHITE)
            binding.btnOption3.setBackgroundColor(Color.WHITE)
            binding.btnOption4.setBackgroundColor(Color.WHITE)
        }
    }

    private fun isValidQuestion(selectOption: String): Boolean {
        val answer = questionList[currentQuestion].answer.trim().replace(" ", "")

        if (answer == selectOption) {
            // answer is correct
            questionScore += 10
            currentQuestion++
            return true
        } else {
            // you are loss
            binding.viewFailed.visibility = View.VISIBLE
            binding.layoutQuiz.visibility = View.GONE
            navigation()
            Toast.makeText(this, "Your get $questionScore coin. Try Again...", Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    private fun navigation() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (questionScore == questionList.size * 10) {
                spinChance++
            }

            questionScore += totalCoin.toInt()
            spinChance += totalChange.toInt()

            //put
            val spinData = Spin(questionScore.toString(), spinChance.toString())
            spinViewModel.setSpinData(spinData)

            startActivity(Intent(this, DashboardActivity::class.java))

            spinChance = 0
            currentQuestion = 0
            questionScore = 0

        }, 2000)
    }
}