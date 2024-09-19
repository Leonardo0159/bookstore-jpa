package com.bookstore.jpa.controllers

import com.bookstore.jpa.services.UolOAuth2Sevice
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/uol")
class UolOAuth2Controller(
    private val uolOAuth2Sevice: UolOAuth2Sevice
) {

    @GetMapping("/login")
    fun login(): RedirectView {
        val url = uolOAuth2Sevice.buildOauthUrl()
        return RedirectView(url)
    }

    @GetMapping("/callback")
    fun handleUolCallback(@RequestParam code: String): Any {
        val accessToken = uolOAuth2Sevice.getAccessToken(code)
        return ResponseEntity.ok(accessToken)
    }

}
