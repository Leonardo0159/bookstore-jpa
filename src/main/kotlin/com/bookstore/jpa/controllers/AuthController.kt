package com.bookstore.jpa.controllers

import com.bookstore.jpa.dto.LoginRequestDTO
import com.bookstore.jpa.dto.RegisterRequestDTO
import com.bookstore.jpa.dto.ResponseDTO
import com.bookstore.jpa.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody body: LoginRequestDTO): ResponseEntity<ResponseDTO> {
        val response = authService.login(body)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequestDTO): ResponseEntity<ResponseDTO> {
        val response = authService.register(body)
        return ResponseEntity.ok(response)
    }
}