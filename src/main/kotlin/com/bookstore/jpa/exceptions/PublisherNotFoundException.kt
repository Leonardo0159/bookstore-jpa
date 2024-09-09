package com.bookstore.jpa.exceptions

class PublisherNotFoundException(message: String = "Publisher not found!") : RuntimeException(message)