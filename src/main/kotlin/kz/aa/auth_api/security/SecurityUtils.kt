package kz.aa.auth_api.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

object SecurityUtils {
    val log = LoggerFactory.getLogger("SecurityUtil")

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    val currentUsername: Optional<String>
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null) {
                log.debug("no authentication in security context found")
                return Optional.empty()
            }
            var username: String? = null
            if (authentication.principal is UserDetails) {
                val springSecurityUser = authentication.principal as UserDetails
                username = springSecurityUser.username
            } else if (authentication.principal is String) {
                username = authentication.principal as String
            }
            log.debug("found username '{}' in security context", username)
            return Optional.ofNullable(username)
        }
}