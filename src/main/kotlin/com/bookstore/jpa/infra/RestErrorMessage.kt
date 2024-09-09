package com.bookstore.jpa.infra

import org.springframework.http.HttpStatus

data class RestErrorMessage(
    val status: HttpStatus,
    val message: String
)