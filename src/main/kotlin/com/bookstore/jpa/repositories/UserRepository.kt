package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<UserModel, Long> {

    fun findByEmail(email: String): Optional<UserModel>

    fun existsByEmail(email: String): Boolean
}