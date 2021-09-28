package kz.aa.auth_api.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class RegistrationDto(
    @JsonProperty(required = true)
    var login: String,
    @JsonProperty(required = true)
    var password: String,
    @JsonProperty(required = true)
    var firstName: String,
    @JsonProperty(required = true)
    var lastname: String,
    @JsonProperty(required = true)
    var email: String,
) : Serializable