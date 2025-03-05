package com.example.stationery.security

import com.example.stationery.users.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@Component
class JWT(
    val properties: TokenProperties
) {
    fun createToken(user: User): String =
        UserToken(user).let {
            Jwts.builder().json(JacksonSerializer())
                .signWith(Keys.hmacShaKeyFor(properties.secret.toByteArray()))
                .subject(user.id.toString())
                .issuedAt(utcNow().toDate())
                .expiration(
                    utcNow().plusHours(
                        if (it.isAdmin) properties.adminExpireHours else properties.expireHours
                    ).toDate()
                )
                .issuer(properties.issuer)
                .claim(USER_FIELD, it)
                .compact()
        }

    fun extract(req: HttpServletRequest): Authentication? {
        try {
            val header = req.getHeader(AUTHORIZATION)
            if (header == null || !header.startsWith("Bearer ")) return null
            val token = header.removePrefix("Bearer ")
            if (token.isEmpty()) return null
            val claims = Jwts.parser().json(JacksonDeserializer(mapOf(USER_FIELD to UserToken::class.java)))
                .verifyWith(Keys.hmacShaKeyFor(properties.secret.toByteArray()))
                .build()
                .parseSignedClaims(token).payload
            if (claims.issuer != properties.issuer) return null
            return claims.get("user", UserToken::class.java).toAuthentication()
        } catch (e: Throwable) {
            log.warn("Token rejected", e)
            return null
        }
    }

    companion object {
        const val USER_FIELD = "user"
        private fun utcNow() = ZonedDateTime.now(ZoneOffset.UTC)
        private fun ZonedDateTime.toDate(): Date = Date.from(this.toInstant())

        private fun UserToken.toAuthentication(): Authentication {
            val authorities = this.roles.map {
                SimpleGrantedAuthority("ROLE_${it.takeIf { it.startsWith("ROLE_") } ?: it}")
            }
            return UsernamePasswordAuthenticationToken(this, id, authorities)
        }
    }
}
