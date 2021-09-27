package kz.aa.auth_api.models.dto

import java.io.Serializable

class LoginDto(
    val login: String,
    val password: String
) : Serializable