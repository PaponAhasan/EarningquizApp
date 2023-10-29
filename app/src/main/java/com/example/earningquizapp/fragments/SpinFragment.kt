package com.example.earningquizapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.databinding.FragmentSpinBinding
import com.example.earningquizapp.model.History
import com.example.earningquizapp.model.Spin
import com.example.earningquizapp.viewmodel.SpinViewModel
import kotlin.random.Random

class SpinFragment : Fragment() {

    private val spinViewModel by lazy {
        ViewModelProvider(this)[SpinViewModel::class.java]
    }

    private var totalCoin = 0
    private var totalChange = 0

    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemTitles = arrayOf(100, 0, 200, 0, 500, 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinViewModel.getSpinData()
        spinViewModel.spinInfoResponse.observe(viewLifecycleOwner) { spin ->
            totalCoin = spin?.coin?.toInt() ?: 0
            totalChange = spin?.chance?.toInt() ?: 0
            binding.tvChange.text = totalChange.toString()

            if (totalChange <= 0) {
                binding.btnSpin.isEnabled = false
            }

            binding.btnSpin.setOnClickListener {

                binding.btnSpin.isEnabled = false
                totalChange--
                binding.tvChange.text = totalChange.toString()

                val spin = Random.nextInt(6)
                val degree = 60f * spin

                timer = object : CountDownTimer(50000, 50) {
                    var rotation = 0f

                    override fun onTick(millisUntilFinished: Long) {
                        rotation += 5f
                        if (rotation >= degree) {
                            rotation = degree
                            timer.cancel()
                            showResult(itemTitles[spin])
                        }
                        binding.ivSpinStoper.rotation = rotation
                    }

                    override fun onFinish() {}
                }.start()
            }
        }
    }

    private fun showResult(item: Int) {
        if (item == 0) {
            Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show()
        } else {
            totalCoin += item
            Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()

            val history = History(item.toString(), System.currentTimeMillis().toString(), false)
            spinViewModel.putSpinHistory(history)
        }

        val spinData = Spin(totalCoin.toString(), totalChange.toString())
        spinViewModel.setSpinData(spinData)

        binding.btnSpin.isEnabled = totalChange != 0
    }
}