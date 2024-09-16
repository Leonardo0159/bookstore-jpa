package com.bookstore.jpa.dto

data class LoginRequestDTO(
    val email: String,
    val password: String,
)