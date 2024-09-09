package com.bookstore.jpa.infra

import com.bookstore.jpa.exceptions.AuthorNotFoundException
import com.bookstore.jpa.exceptions.BookNotFoundException
import com.bookstore.jpa.exceptions.PublisherNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthorNotFoundException::class)
    fun authorNotFoundHandler(exception: AuthorNotFoundException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.NOT_FOUND,
            message = exception.message ?: "Author not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse)
    }

    @ExceptionHandler(BookNotFoundException::class)
    fun bookNotFoundHandler(exception: BookNotFoundException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.NOT_FOUND,
            message = exception.message ?: "Book not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse)
    }

    @ExceptionHandler(PublisherNotFoundException::class)
    fun publisherNotFoundHandler(exception: PublisherNotFoundException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.NOT_FOUND,
            message = exception.message ?: "Publisher not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse)
    }


}
