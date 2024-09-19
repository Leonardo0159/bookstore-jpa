package com.bookstore.jpa.services

import com.bookstore.jpa.view.vo.uol.OAuthResponseToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.cdimascio.dotenv.dotenv
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.BodyInserters
import java.net.URLEncoder
import org.springframework.web.reactive.function.client.WebClient
import java.text.SimpleDateFormat
import java.util.*


@Service
class UolOAuth2Sevice(
    @Qualifier("defaultRestTemplate") val restTemplate: RestTemplate,
) {

    private val clientId: String = dotenv()["UOL_CLIENT_ID"]
    private val clientSecret: String = dotenv()["UOL_SECRET_KEY"]
    private val uolBaseUrl: String = dotenv()["UOL_BASE_URL"]
    private val redirectUri: String = dotenv()["UOL_REDIRECT_URI"]
    private val webClient = WebClient.create("https://api.uol.com.br")

    fun buildOauthUrl(): String {
        val uolOAuthUrl = "${uolBaseUrl}/oauth/auth" +
                "?response_type=code" +
                "&redirect_uri=${encode(redirectUri)}" +
                "&client_id=${encode(clientId)}" +
                "&login_params=t%3Ddefault"
        return uolOAuthUrl
    }

    private fun encode(strToEncode: String): String? {
        return URLEncoder.encode(strToEncode, "UTF-8");
    }

    @Throws(HttpClientErrorException::class)
    fun getAccessToken(code: String): OAuthResponseToken? {

        val urlOAuthToken = "${uolBaseUrl}/oauth/token"

        val bodyParams = getBodyParams(
            code = code, clientId = clientId,
            clientSecret = clientSecret, redirectUri = redirectUri, grantType = "authorization_code"
        )

        val responseEntity: ResponseEntity<String>?

        try {
            responseEntity = postRequest(urlOAuthToken, bodyParams)
        } catch (e: HttpClientErrorException) {
            throw e
        }
        return jacksonObjectMapper().readValue(responseEntity.body, OAuthResponseToken::class.java)
    }

    private fun getBodyParams(
        code: String, clientId: String, clientSecret: String, redirectUri: String,
        grantType: String
    ): MultiValueMap<String, String> {
        val body = LinkedMultiValueMap<String, String>()
        body.apply {
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("redirect_uri", redirectUri)
            add("refresh", "true") // TODO: refresh via config por parceiro
            code.let {
                add("code", code)
                add("grant_type", grantType)
            }
        }
        return body
    }

    @Throws(HttpClientErrorException::class)
    private fun postRequest(url: String, body: MultiValueMap<String, String>): ResponseEntity<String> {

        val response =
            restTemplate.exchange(url, HttpMethod.POST, createCleanEntityFormUrlEncoded(body), String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            response
        } else {
            ResponseEntity(response.body, HttpStatus.UNAUTHORIZED)
        }
    }

    private fun createCleanEntityFormUrlEncoded(body: MultiValueMap<String, String>): HttpEntity<*> {
        val headers = getHttpHeadersFormUrlEncoded()

        return HttpEntity(body, headers)
    }

    private fun getHttpHeadersFormUrlEncoded(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Date", getDateGMTString())
        return headers
    }

    private fun getDateGMTString(): String {
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        return sdf.format(Date().time)
    }
}