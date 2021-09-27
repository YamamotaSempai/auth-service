package kz.aa.auth_api.repositories

import kz.aa.auth_api.models.SecUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<SecUser, Long> {
    fun findByLogin(login: String): SecUser?
}