package com.example.a3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.App
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.api.model.UserResponse
import com.example.a3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class ProfileViewModel (val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var user = MutableLiveData<UserResponse?>()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUser(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get tasks response: ${response.body()}")

                    val userList = response.body()
                    userList?.let {
                        user.value = userList
                    }


                } else {
                    Log.d(TAG, "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "ProfileViewModel - getUserData() failed with exception: ${e.message}")
            }
        }
    }
}