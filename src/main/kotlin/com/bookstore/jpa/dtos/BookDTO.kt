package com.bookstore.jpa.dto

data class BookDTO(
    val title: String,
    val publisherId: Long,
    val authorIds: List<Long>,
    val review: String
)