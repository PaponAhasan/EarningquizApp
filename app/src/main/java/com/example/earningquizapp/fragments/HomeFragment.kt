package com.example.earningquizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.earningquizapp.R
import com.example.earningquizapp.adapter.CategoryAdapter
import com.example.earningquizapp.databinding.FragmentHomeBinding
import com.example.earningquizapp.model.Category

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val categoryList = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryList.clear()

        categoryList.add(Category(R.drawable.ic_scince, "Science"))
        categoryList.add(Category(R.drawable.ic_mathmetic, "Math"))
        categoryList.add(Category(R.drawable.ic_englishs, "English"))
        categoryList.add(Category(R.drawable.ic_history, "History"))

        binding.rvItem.layoutManager = GridLayoutManager(context, 2)
        binding.rvItem.adapter = CategoryAdapter(categoryList, context)
        binding.rvItem.setHasFixedSize(true)
    }
}