package com.bookstore.jpa.exceptions

class BookNotFoundException(message: String = "Book not found!") : RuntimeException(message)