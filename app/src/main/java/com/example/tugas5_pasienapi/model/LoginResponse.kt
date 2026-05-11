package com.example.tugas5_pasienapi.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)