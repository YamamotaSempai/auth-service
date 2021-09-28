package kz.aa.auth_api.services.impl

import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.repositories.UserRepository
import kz.aa.auth_api.services.UserFinder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserFinderImpl @Autowired constructor(private val userRepository: UserRepository) : UserFinder {

    override fun getByLogin(login: String): SecUser? {
        return userRepository.findByLogin(login)
    }

    override fun getAll(pageable: Pageable): Page<SecUser> {
        return userRepository.findAll(pageable)
    }
}