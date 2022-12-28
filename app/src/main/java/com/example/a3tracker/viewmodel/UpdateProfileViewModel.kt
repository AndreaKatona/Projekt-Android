package com.example.a3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.App
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.api.model.UpdateRequestResponse
import com.example.a3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProfileViewModel  (private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun update(lastName: String,firstName: String,location: String,phoneNumber: String,imageUrl: String) {
        val requestBody = UpdateRequestResponse(lastName,firstName,location,phoneNumber,imageUrl)
        viewModelScope.launch {
            executeUpdate(requestBody)
        }
    }

    private suspend fun executeUpdate(requestBody: UpdateRequestResponse) {
        try {
            val token: String? = App.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN, "Empty token!")

            val response = withContext(Dispatchers.IO) {
               token?.let { repository.updateUser(it, requestBody) }
           }

            if (response != null) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Update response: ${response.body()}")

                } else {
                    Log.d(TAG, "Update error response: ${response.message()}")
                    isSuccessful.value = false
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, " UpdateProfileViewModel - updateUser() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}