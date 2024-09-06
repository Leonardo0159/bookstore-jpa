package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.AuthorModel
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<AuthorModel, Long> {
}