package kz.aa.auth_api.exceptions

import org.springframework.security.core.AuthenticationException

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
class UserNotActivatedException(message: String?) : AuthenticationException(message) {
    companion object {
        private const val serialVersionUID = -1126699074574529145L
    }
}