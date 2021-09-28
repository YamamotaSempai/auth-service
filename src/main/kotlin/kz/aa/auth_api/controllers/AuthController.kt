package kz.aa.auth_api.controllers

import com.fasterxml.jackson.annotation.JsonProperty
import kz.aa.auth_api.jwt.JWTFilter
import kz.aa.auth_api.jwt.TokenProvider
import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.LoginDto
import kz.aa.auth_api.models.dto.RegistrationDto
import kz.aa.auth_api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenProvider: TokenProvider,
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: @Valid LoginDto): ResponseEntity<JWTToken> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.login, loginDto.password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        val httpHeaders = HttpHeaders()
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer $jwt")
        return ResponseEntity(JWTToken(jwt), httpHeaders, HttpStatus.OK)
    }

    @PostMapping("/registration")
    fun registration(@RequestBody registrationDto: @Valid RegistrationDto): ResponseEntity<SecUser> {
        return ResponseEntity.ok(userService.create(registrationDto))
    }

    class JWTToken(@get:JsonProperty("id_token") val idToken: String)
}