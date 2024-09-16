package com.bookstore.jpa.exceptions

class UserNotFoundException(message: String = "User not found!") : RuntimeException(message)