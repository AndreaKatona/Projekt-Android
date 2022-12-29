package com.example.a3tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.a3tracker.R
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.databinding.FragmentAddTaskBinding
import com.example.a3tracker.databinding.FragmentSettingsBinding
import com.example.a3tracker.viewmodel.*


class AddTaskFragment : Fragment() {


    private lateinit var departments: DepartmentsViewModel
    private lateinit var binding : FragmentAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = DepartmentsViewModelFactory(ThreeTrackerRepository())
        departments  = ViewModelProvider(this,factory)[DepartmentsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddTaskBinding.inflate(inflater)

        val factory = DepartmentsViewModelFactory(ThreeTrackerRepository())
        departments  = ViewModelProvider(this,factory)[DepartmentsViewModel::class.java]

        return  binding.root


    }


}