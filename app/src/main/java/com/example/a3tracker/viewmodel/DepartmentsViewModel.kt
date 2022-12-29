package com.example.a3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3tracker.App
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.api.model.DepartmentResponse
import com.example.a3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class DepartmentsViewModel (val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var departments = MutableLiveData<List<DepartmentResponse?>?>()

    init {
        getDepartments()
    }
    fun getDepartments()
    {
        viewModelScope.launch {
            getDepartment()
        }
    }

    private fun getDepartment() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getDepartment(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get tasks response: ${response.body()}")

                    val departmentList = response.body()
                    departmentList?.let {
                        departments.value = departmentList
                    }


                } else {
                    Log.d(TAG, "Get departments error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "DepartmentViewModel - getDepartment() failed with exception: ${e.message}")
            }
        }
    }
}