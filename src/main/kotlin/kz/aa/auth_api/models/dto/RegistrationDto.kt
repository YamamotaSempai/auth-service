package kz.aa.auth_api.models.dto

import java.io.Serializable

class RegistrationDto(
    var login: String,
    var password: String,
    var firstName: String,
    var lastname: String,
    var email: String,
) : Serializable