package kz.aa.auth_api.services.impl

import kz.aa.auth_api.exceptions.UserException
import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.RegistrationDto
import kz.aa.auth_api.models.dto.UpdateDto
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
        throwIfUserFound(registrationDto.login, registrationDto.email)
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

    @Transactional
    override fun update(login: String, updateDto: UpdateDto): SecUser {
        var foundUser = userRepository.findByLogin(login) ?: throw UserException(USER_DOES_NOT_EXIST)
        val (firstname, lastname, email) = updateDto
        foundUser = foundUser.copy(firstname = firstname, lastname = lastname, email = email)
        return userRepository.save(foundUser)
    }

    private fun throwIfUserFound(login: String, email: String) {
        if (userRepository.findByLogin(login) != null || userRepository.findByEmail(email) != null) {
            throw UserException(USER_ALREADY_EXISTS)
        }
    }

    override fun getByLogin(login: String): SecUser? {
        return userRepository.findByLogin(login)
    }

    private companion object {
        const val USER_ALREADY_EXISTS = "User already exists!"
        const val USER_DOES_NOT_EXIST = "User does not exist!"
    }
}