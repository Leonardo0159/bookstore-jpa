package com.bookstore.jpa.exceptions

class EmailAlreadyUsedException(message: String = "Email already in use!") : RuntimeException(message)