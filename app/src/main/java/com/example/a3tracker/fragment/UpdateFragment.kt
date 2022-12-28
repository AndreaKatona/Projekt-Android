package com.example.a3tracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.a3tracker.R
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.databinding.FragmentUpdateBinding
import com.example.a3tracker.viewmodel.ProfileViewModel
import com.example.a3tracker.viewmodel.ProfileViewModelFactory

class UpdateFragment : Fragment() {


    private lateinit var binding : FragmentUpdateBinding
    private lateinit var profileViewModel : ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater)
        val factory = ProfileViewModelFactory(ThreeTrackerRepository())
        profileViewModel = ViewModelProvider(requireActivity(),factory)[ProfileViewModel::class.java]


        setValues()
        return binding.root
    }


    fun setValues()
    {
        binding.updateName.setText(profileViewModel.user.value?.first_name)
        binding.updateAddress.setText(profileViewModel.user.value?.location)
        binding.updateNumber.setText(profileViewModel.user.value?.number)
    }

}