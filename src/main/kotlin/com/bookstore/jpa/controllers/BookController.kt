package com.bookstore.jpa.controllers

import com.bookstore.jpa.dto.BookDTO
import com.bookstore.jpa.models.BookModel
import com.bookstore.jpa.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookModel>> {
        val books = bookService.findAll()
        return ResponseEntity.ok(books)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<BookModel> {
        val book = bookService.findById(id)
        return if (book.isPresent) {
            ResponseEntity.ok(book.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createBook(@RequestBody bookDTO: BookDTO): ResponseEntity<BookModel> {
        val createdBook = bookService.save(bookDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook)
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: Long,
        @RequestBody bookDTO: BookDTO
    ): ResponseEntity<BookModel> {
        val updatedBook = bookService.update(id, bookDTO)
        return if (updatedBook != null) {
            ResponseEntity.ok(updatedBook)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Void> {
        return if (bookService.findById(id).isPresent) {
            bookService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}