package com.example.a3tracker.api.model

import com.google.gson.annotations.SerializedName

class UserUpdateResponse {
    @SerializedName("userId")
    var userId: Int,
    {
        override fun toString(): String {
            return "LoginResponse(" +
                    "userId='$userId',"
        }
}