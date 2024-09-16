package com.bookstore.jpa.infra.security

import com.bookstore.jpa.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    @Autowired private val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByEmail(username).orElseThrow {
            UsernameNotFoundException("User not found")
        }
        return org.springframework.security.core.userdetails.User(
            user.email, user.password, emptyList()
        )
    }
}

