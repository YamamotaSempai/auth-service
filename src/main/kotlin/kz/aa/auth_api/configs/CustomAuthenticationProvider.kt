package kz.aa.auth_api.configs

import kz.aa.auth_api.exceptions.UserNotActivatedException
import kz.aa.auth_api.services.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userService: UserService,
    private val bCryptPasswordEncoder: PasswordEncoder
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val login = authentication.name
        val password = authentication.credentials.toString()
        val findUser = userService.getByLogin(login)
        return if (findUser != null && bCryptPasswordEncoder.matches(password, findUser.password)) {
            // use the credentials
            // and authenticate against the third-party system
            UsernamePasswordAuthenticationToken(
                login,
                password,
                setOf()
            )
        } else {
            throw UserNotActivatedException(userNotActivatedException)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    companion object {
        const val userNotActivatedException: String = "User is not activated!"
    }

}