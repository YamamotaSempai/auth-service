package kz.aa.auth_api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserException(message: String) : RuntimeException(message)

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
class UserNotActivatedException(message: String) : AuthenticationException(message) {
    companion object {
        private const val serialVersionUID = -1126699074574529145L
    }
}