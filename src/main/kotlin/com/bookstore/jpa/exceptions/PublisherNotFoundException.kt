package com.bookstore.jpa.exceptions

class PublisherNotFoundException : RuntimeException {
    constructor() : super("Publisher not found!")
    constructor(message: String) : super(message)
}
