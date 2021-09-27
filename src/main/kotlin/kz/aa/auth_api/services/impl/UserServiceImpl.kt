package kz.aa.auth_api.services.impl

import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.RegistrationDto
import kz.aa.auth_api.repositories.UserRepository
import kz.aa.auth_api.services.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun create(registrationDto: RegistrationDto): SecUser {
        return userRepository.save<SecUser>(
            SecUser(
                email = registrationDto.email,
                activated = true,
                firstname = registrationDto.firstName,
                lastname = registrationDto.lastname,
                login = registrationDto.login,
                password = bCryptPasswordEncoder.encode(registrationDto.password),
                roles = setOf(),
            )
        )
    }

    override fun getByLogin(login: String): SecUser? {
        return userRepository.findByLogin(login)
    }
}