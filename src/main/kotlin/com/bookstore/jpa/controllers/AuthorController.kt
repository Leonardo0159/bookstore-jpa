package com.bookstore.jpa.controllers

import com.bookstore.jpa.models.AuthorModel
import com.bookstore.jpa.services.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping
    fun getAllAuthors(): ResponseEntity<List<AuthorModel>> {
        val authors = authorService.findAll()
        return ResponseEntity.ok(authors)
    }

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Long): ResponseEntity<AuthorModel> {
        val author = authorService.findById(id)
        return ResponseEntity.ok(author)
    }

    @PostMapping
    fun createAuthor(@RequestBody author: AuthorModel): ResponseEntity<AuthorModel> {
        val createdAuthor = authorService.save(author)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor)
    }

    @PutMapping("/{id}")
    fun updateAuthor(
        @PathVariable id: Long,
        @RequestBody author: AuthorModel
    ): ResponseEntity<AuthorModel> {
        val updatedAuthor = authorService.update(id, author)
        return ResponseEntity.ok(updatedAuthor)
    }

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Long): ResponseEntity<Void> {
        authorService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}