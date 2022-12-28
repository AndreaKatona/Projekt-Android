package com.example.a3tracker.api

import com.example.a3tracker.api.model.*
import retrofit2.Response

class ThreeTrackerRepository {

    /**
     * 'suspend' keyword means that this function can be blocking.
     * We need to be aware that we can only call them from within a coroutine or an other suspend function!
     */
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getTasks(token)
    }

    suspend fun getUser(token: String): Response<UserResponse>
    {
        return RetrofitInstance.USER_API_SERVICE.getUser(token)
    }

    suspend fun getDepartment(token: String): Response<List<DepartmentResponse>>
    {
        return RetrofitInstance.USER_API_SERVICE.getDepartment(token)
    }
}