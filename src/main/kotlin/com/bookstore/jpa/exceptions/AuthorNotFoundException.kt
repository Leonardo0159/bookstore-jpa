package com.bookstore.jpa.exceptions

class AuthorNotFoundException : RuntimeException {
    constructor() : super("Author not found!")
    constructor(message: String) : super(message)
}
