package kz.aa.auth_api.services

import kz.aa.auth_api.models.SecUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserFinder {
    fun getByLogin(login: String): SecUser?
    fun getAll(pageable: Pageable): Page<SecUser>
}