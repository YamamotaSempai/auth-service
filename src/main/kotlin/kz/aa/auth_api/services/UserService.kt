package kz.aa.auth_api.services

import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.RegistrationDto
import kz.aa.auth_api.models.dto.UpdateDto
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
interface UserService {
    fun create(registrationDto: RegistrationDto): SecUser
    fun update(login: String, updateDto: @Valid UpdateDto): SecUser
}