package com.example.a3tracker.api.model

import com.google.gson.annotations.SerializedName


data class DepartmentResponse(
    @SerializedName("ID")
    var Id : Int,
    @SerializedName("name")
    var departmentName : String
)
