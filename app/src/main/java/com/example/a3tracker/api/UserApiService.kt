package com.example.a3tracker.api

import com.example.a3tracker.api.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @POST(BackendConstants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequestBody): Response<LoginResponse>

    @GET(BackendConstants.GET_TASKS_URL)
    suspend fun getTasks(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<TaskResponse>>

    @GET(BackendConstants.GET_USERS_URL)
    suspend fun getUser(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<UserResponse>

    @GET(BackendConstants.GET_DEPARTMENT_URL)
    suspend fun getDepartment(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<DepartmentResponse>>

    @POST(BackendConstants.UPDATE_USER)
    suspend fun updateUser(@Header(BackendConstants.HEADER_TOKEN) token: String,@Body updateRequestResponse: UpdateRequestResponse):Response<String>


}