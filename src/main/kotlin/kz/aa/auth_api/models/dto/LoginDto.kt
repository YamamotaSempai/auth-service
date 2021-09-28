package kz.aa.auth_api.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class LoginDto(
    @JsonProperty(required = true)
    val login: String,
    @JsonProperty(required = true)
    val password: String
) : Serializable