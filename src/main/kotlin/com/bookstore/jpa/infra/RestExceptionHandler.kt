package com.bookstore.jpa.infra

import com.bookstore.jpa.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundHandler(exception: UserNotFoundException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.NOT_FOUND,
            message = exception.message ?: "User not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun invalidCredentialsHandler(exception: InvalidCredentialsException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.UNAUTHORIZED,
            message = exception.message ?: "Invalid credentials"
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse)
    }

    @ExceptionHandler(EmailAlreadyUsedException::class)
    fun emailAlreadyUsedHandler(exception: EmailAlreadyUsedException): ResponseEntity<RestErrorMessage> {
        val threatResponse = RestErrorMessage(
            status = HttpStatus.BAD_REQUEST,
            message = exception.message ?: "Email already in use"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse)
    }
}
