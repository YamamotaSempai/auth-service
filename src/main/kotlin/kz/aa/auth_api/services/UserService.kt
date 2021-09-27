package kz.aa.auth_api.services

import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.RegistrationDto
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun create(registrationDto: RegistrationDto): SecUser
    fun getByLogin(login: String): SecUser?
}