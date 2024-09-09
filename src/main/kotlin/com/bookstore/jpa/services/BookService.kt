package com.bookstore.jpa.services

import com.bookstore.jpa.dto.BookDTO
import com.bookstore.jpa.exceptions.AuthorNotFoundException
import com.bookstore.jpa.exceptions.BookNotFoundException
import com.bookstore.jpa.exceptions.PublisherNotFoundException
import com.bookstore.jpa.models.BookModel
import com.bookstore.jpa.models.ReviewModel
import com.bookstore.jpa.repositories.AuthorRepository
import com.bookstore.jpa.repositories.BookRepository
import com.bookstore.jpa.repositories.PublisherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val publisherRepository: PublisherRepository,
    private val authorRepository: AuthorRepository
) {

    fun findAll(): List<BookModel> {
        return bookRepository.findAll()
    }

    fun findById(id: Long): BookModel {
        return bookRepository.findById(id).orElseThrow() { BookNotFoundException("Book id ${id} not found") }
    }

    @Transactional
    fun save(bookDTO: BookDTO): BookModel {
        // Busca o Publisher pelo ID
        val publisher = publisherRepository.findById(bookDTO.publisherId)
            .orElseThrow { PublisherNotFoundException() }

        // Busca os Authors pelos IDs
        val authors = authorRepository.findAllById(bookDTO.authorIds)
        if (authors.size != bookDTO.authorIds.size) {
            throw AuthorNotFoundException()
        }

        // Utilizar pattern factory
        // Cria o Review
        val review = ReviewModel(
            comment = bookDTO.review,
            book = null // Relacionamento será preenchido após salvar o livro
        )

        // Cria o BookModel
        val book = BookModel(
            title = bookDTO.title,
            publisher = publisher,
            authors = authors,
            review = review
        )
        review.book = book // Estabelece o relacionamento de volta

        return bookRepository.save(book)
    }

    @Transactional
    fun update(id: Long, bookDTO: BookDTO): BookModel? {
        val existingBook = bookRepository.findById(id)
        return if (existingBook.isPresent) {
            val publisher = publisherRepository.findById(bookDTO.publisherId)
                .orElseThrow { PublisherNotFoundException() }

            val authors = authorRepository.findAllById(bookDTO.authorIds)
            if (authors.size != bookDTO.authorIds.size) {
                throw AuthorNotFoundException()
            }

            val updatedBook = existingBook.get().copy(
                title = bookDTO.title,
                publisher = publisher,
                authors = authors,
                review = existingBook.get().review?.apply { comment = bookDTO.review }
            )

            bookRepository.save(updatedBook)
        } else {
            throw BookNotFoundException("Book id ${id} not found")
        }
    }

    @Transactional
    fun deleteById(id: Long) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id)
        } else {
            throw BookNotFoundException("Book id ${id} not found")
        }
    }
}
