package com.bookstore.jpa.services

import com.bookstore.jpa.models.AuthorModel
import com.bookstore.jpa.repositories.AuthorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {

    fun findAll(): List<AuthorModel> {
        return authorRepository.findAll()
    }

    fun findById(id: Long): Optional<AuthorModel> {
        return authorRepository.findById(id)
    }

    @Transactional
    fun save(author: AuthorModel): AuthorModel {
        return authorRepository.save(author)
    }

    @Transactional
    fun update(id: Long, author: AuthorModel): AuthorModel? {
        val existingAuthor = authorRepository.findById(id)
        return if (existingAuthor.isPresent) {
            val updatedAuthor = existingAuthor.get().copy(
                name = author.name
            )
            authorRepository.save(updatedAuthor)
        } else {
            null
        }
    }

    @Transactional
    fun deleteById(id: Long) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id)
        }
    }
}