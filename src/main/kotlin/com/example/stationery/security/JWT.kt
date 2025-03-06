package com.example.stationery.security

import com.example.stationery.users.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date
import org.slf4j.LoggerFactory

@Component
class JWT(
    private val properties: TokenProperties
) {

    private val logger = LoggerFactory.getLogger(JWT::class.java)

    fun createToken(user: User): String {
        val userToken = UserToken(user)
        return Jwts.builder()
            .json(JacksonSerializer())
            .signWith(Keys.hmacShaKeyFor(properties.secret.toByteArray()))
            .subject(user.id.toString())
            .issuedAt(utcNow().toDate())
            .expiration(utcNow().plusHours(getExpirationHours(userToken)).toDate())
            .issuer(properties.issuer)
            .claim(USER_FIELD, userToken)
            .compact()
    }

    fun extract(req: HttpServletRequest): Authentication? {
        val header = req.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        if (!header.startsWith("Bearer ")) return null
        val token = header.removePrefix("Bearer ").takeIf { it.isNotEmpty() } ?: return null
        return try {
            val claims = parseClaims(token)
            if (claims.issuer != properties.issuer) return null
            claims.get(USER_FIELD, UserToken::class.java)?.toAuthentication()
        } catch (e: Exception) {
            logger.warn("Token rejeitado", e)
            null
        }
    }

    private fun getExpirationHours(userToken: UserToken) =
        if (userToken.isAdmin) properties.adminExpireHours else properties.expireHours

    private fun parseClaims(token: String) =
        Jwts.parser()
            .json(JacksonDeserializer(mapOf(USER_FIELD to UserToken::class.java)))
            .verifyWith(Keys.hmacShaKeyFor(properties.secret.toByteArray()))
            .build()
            .parseSignedClaims(token).payload

    companion object {
        const val USER_FIELD = "user"
        private fun utcNow() = ZonedDateTime.now(ZoneOffset.UTC)
        private fun ZonedDateTime.toDate(): Date = Date.from(this.toInstant())
        private fun UserToken.toAuthentication(): Authentication {
            val authorities = roles.map { SimpleGrantedAuthority("ROLE_${it.takeIf { it.startsWith("ROLE_") } ?: it}") }
            return UsernamePasswordAuthenticationToken(this, id, authorities)
        }
    }
}
