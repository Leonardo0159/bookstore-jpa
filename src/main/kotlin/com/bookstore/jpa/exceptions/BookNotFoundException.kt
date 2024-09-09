package com.bookstore.jpa.exceptions

class BookNotFoundException : RuntimeException {
    constructor() : super("Book not found!")
    constructor(message: String) : super(message)
}
