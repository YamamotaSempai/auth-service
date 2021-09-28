package kz.aa.auth_api.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Service
class TokenProvider(
    @param:Value("\${jwt.secret}") private val base64Secret: String,
    @param:Value("\${jwt.expiration}") private var tokenValidityInSeconds: Long
) : InitializingBean {

    private val log = LoggerFactory.getLogger("TokenProvider")
    private val key: Key = Keys.hmacShaKeyFor(base64Secret.toByteArray(StandardCharsets.UTF_8))

    override fun afterPropertiesSet() {}

    fun createToken(authentication: Authentication): String {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        val now = Date().time
        val validity = Date(now + tokenValidityInSeconds * 1000)
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val jwtParser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
        val claims = jwtParser.parseClaimsJws(token).body
        val authorities: Collection<GrantedAuthority> = Arrays.stream(
            claims[AUTHORITIES_KEY].toString().split(",").toTypedArray()
        )
            .map { role: String? -> SimpleGrantedAuthority(role) }
            .collect(Collectors.toList())
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            val jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
            jwtParser.parseClaimsJws(authToken)
            return true
        } catch (e: SecurityException) {
            log.info("Invalid JWT signature.")
            log.trace("Invalid JWT signature trace: {}", e)
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT signature.")
            log.trace("Invalid JWT signature trace: {}", e)
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token.")
            log.trace("Expired JWT token trace: {}", e)
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token.")
            log.trace("Unsupported JWT token trace: {}", e)
        } catch (e: IllegalArgumentException) {
            log.info("JWT token compact of handler are invalid.")
            log.trace("JWT token compact of handler are invalid trace: {}", e)
        }
        return false
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }
}