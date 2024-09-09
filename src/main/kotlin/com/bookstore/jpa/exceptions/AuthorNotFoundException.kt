package com.bookstore.jpa.exceptions

class AuthorNotFoundException(message: String = "Author not found!") : RuntimeException(message)