package com.example.earningquizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.earningquizapp.R
import com.example.earningquizapp.databinding.FragmentProfileBinding
import com.example.earningquizapp.viewmodel.CustomViewModelFactory
import com.example.earningquizapp.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(requireContext()))[UserViewModel::class.java]
    }

    private var isExpend = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowDown.setOnClickListener {
            if (isExpend) {
                binding.layoutUserInfo.visibility = View.VISIBLE
                binding.ivArrowDown.setImageResource(R.drawable.ic_arrow_up)
            } else {
                binding.layoutUserInfo.visibility = View.GONE
                binding.ivArrowDown.setImageResource(R.drawable.ic_arrow_down)
            }
            isExpend = !isExpend
        }

        viewModel.getUser()
        viewModel.userInfoResponse.observe(viewLifecycleOwner) { user ->
            binding.tvProfileName.text = user?.name
            binding.etName.text = user?.name
            binding.etEamil.text = user?.email
            binding.etPassword.text = user?.password
            binding.etAge.text = user?.age.toString()
        }
    }
}