package kz.aa.auth_api.configs

import kz.aa.auth_api.exceptions.UserException
import kz.aa.auth_api.exceptions.UserNotActivatedException
import kz.aa.auth_api.services.UserFinder
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userFinder: UserFinder,
    private val bCryptPasswordEncoder: PasswordEncoder
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val login = authentication.name
        val password = authentication.credentials.toString()
        val findUser = userFinder.getByLogin(login) ?: throw UserException(USER_DOES_NOT_EXIST)
        if (!bCryptPasswordEncoder.matches(password, findUser.password)) throw UserNotActivatedException(PASSWORD_WRONG)
        return UsernamePasswordAuthenticationToken(
            login,
            password,
            setOf()
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    companion object {
        const val USER_DOES_NOT_EXIST = "User does not exist!"
        const val PASSWORD_WRONG: String = "Password is wrong!"
    }

}