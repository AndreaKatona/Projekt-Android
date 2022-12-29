package com.example.a3tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a3tracker.api.ThreeTrackerRepository

class GetUsersViewModelFactory (private val repository: ThreeTrackerRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetUsersViewModel(repository) as T
    }
}