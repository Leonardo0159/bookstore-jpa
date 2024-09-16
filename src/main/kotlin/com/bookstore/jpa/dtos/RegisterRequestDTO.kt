package com.bookstore.jpa.dto

data class RegisterRequestDTO(
    val name: String,
    val email: String,
    val password: String,
)