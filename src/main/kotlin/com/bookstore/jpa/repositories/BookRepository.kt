package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.BookModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface BookRepository : JpaRepository<BookModel, Long> {

}