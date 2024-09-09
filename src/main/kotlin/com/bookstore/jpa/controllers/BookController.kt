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
        return ResponseEntity.ok(book)
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
        return ResponseEntity.ok(updatedBook)
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Void> {
        bookService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}