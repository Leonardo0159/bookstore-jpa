package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.ReviewModel
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<ReviewModel, Long> {
}