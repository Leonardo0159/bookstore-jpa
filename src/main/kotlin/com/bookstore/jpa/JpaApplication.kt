package com.bookstore.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.dotenv
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

@SpringBootApplication
class JpaApplication

fun main(args: Array<String>) {
	// Inicializa o dotenv para carregar as variáveis de ambiente do .env
	val dotenv = dotenv {
		directory = "."
		ignoreIfMalformed = true
		ignoreIfMissing = true
	}

	// Ignorar a verificação do hostname (APENAS PARA DESENVOLVIMENTO/TESTE)
	HttpsURLConnection.setDefaultHostnameVerifier(HostnameVerifier { _, _ -> true })

	runApplication<JpaApplication>(*args)
}
