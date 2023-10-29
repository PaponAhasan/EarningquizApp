package com.example.earningquizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.R
import com.example.earningquizapp.databinding.FragmentHistoryBinding
import com.example.earningquizapp.databinding.FragmentWithdrawalBinding
import com.example.earningquizapp.model.History
import com.example.earningquizapp.model.Spin
import com.example.earningquizapp.viewmodel.SpinViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WithdrawalFragment : BottomSheetDialogFragment() {

    private val withdrawalBinding: FragmentWithdrawalBinding by lazy {
        FragmentWithdrawalBinding.inflate(layoutInflater)
    }

    private val spinViewModel by lazy {
        ViewModelProvider(this)[SpinViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return withdrawalBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withdrawalBinding.btnTrnsAmount.setOnClickListener {

            val amount = withdrawalBinding.etAmount.text.toString()
            val etPayTm = withdrawalBinding.etPayTm.text.toString()

            if (amount.isNotEmpty()) {

                withdrawalBinding.etAmount.text!!.clear()

                spinViewModel.getSpinData()
                spinViewModel.spinInfoResponse.observe(viewLifecycleOwner) {
                    val coin = it?.coin?.toLong()
                    if (amount.toLong() <= coin!! && amount.toLong() > 0L) {
                        val spinData = Spin((coin - amount.toLong()).toString(), it.chance)
                        spinViewModel.setSpinData(spinData)

                        val history =
                            History(amount, System.currentTimeMillis().toString(), true)
                        spinViewModel.putSpinHistory(history)

                        Toast.makeText(
                            context,
                            "Amount Transfer Successfully!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(context, "Amount out of bound", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "field is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}