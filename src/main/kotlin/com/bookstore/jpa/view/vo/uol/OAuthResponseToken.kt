package com.bookstore.jpa.view.vo.uol

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OAuthResponseToken(
        @JsonProperty("expires_in") val expiresIn: Long,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("refresh_token") val refreshToken: String?)