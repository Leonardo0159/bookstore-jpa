package com.bookstore.jpa.services

import com.bookstore.jpa.dto.LoginRequestDTO
import com.bookstore.jpa.dto.RegisterRequestDTO
import com.bookstore.jpa.dto.ResponseDTO
import com.bookstore.jpa.exceptions.EmailAlreadyUsedException
import com.bookstore.jpa.exceptions.InvalidCredentialsException
import com.bookstore.jpa.infra.security.TokenService
import com.bookstore.jpa.models.UserModel
import com.bookstore.jpa.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService
) {
    fun login(body: LoginRequestDTO): ResponseDTO {
        val user = userRepository.findByEmail(body.email).orElseThrow { InvalidCredentialsException("Invalid email or password") }
        if (passwordEncoder.matches(body.password, user.password)) {
            val token = tokenService.generateToken(user)
            return ResponseDTO(user.name, token)
        } else {
            throw InvalidCredentialsException("Invalid email or password")
        }
    }

    fun register(body: RegisterRequestDTO): ResponseDTO {
        if (userRepository.existsByEmail(body.email)) {
            throw EmailAlreadyUsedException()
        }

        val newUser = UserModel(
            name = body.name,
            email = body.email,
            password = passwordEncoder.encode(body.password)
        )

        userRepository.save(newUser)
        val token = tokenService.generateToken(newUser)
        return ResponseDTO(newUser.name, token)
    }
}