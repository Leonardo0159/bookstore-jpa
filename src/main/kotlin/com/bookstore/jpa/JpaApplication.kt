package com.bookstore.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.dotenv

@SpringBootApplication
class JpaApplication

fun main(args: Array<String>) {
	// Inicializa o dotenv para carregar as variáveis de ambiente do .env
	val dotenv = dotenv {
		directory = "."
		ignoreIfMalformed = true
		ignoreIfMissing = true
	}

	runApplication<JpaApplication>(*args)
}
