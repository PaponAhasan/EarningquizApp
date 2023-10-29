package com.example.earningquizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earningquizapp.R
import com.example.earningquizapp.adapter.CategoryAdapter
import com.example.earningquizapp.adapter.HistoryAdapter
import com.example.earningquizapp.databinding.FragmentHistoryBinding
import com.example.earningquizapp.databinding.FragmentHomeBinding
import com.example.earningquizapp.model.Category
import com.example.earningquizapp.model.History
import com.example.earningquizapp.viewmodel.SpinViewModel

class HistoryFragment : Fragment() {

    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val spinViewModel by lazy {
        ViewModelProvider(this)[SpinViewModel::class.java]
    }

    private var historyList : MutableList<History> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinViewModel.getSpinHistory()
        spinViewModel.spinHistoryResponse.observe(viewLifecycleOwner){
            historyList = it
            historyList.reverse()
            binding.rvHistory.layoutManager = LinearLayoutManager(context)
            binding.rvHistory.adapter = HistoryAdapter(historyList)
            binding.rvHistory.setHasFixedSize(true)
        }
    }
}