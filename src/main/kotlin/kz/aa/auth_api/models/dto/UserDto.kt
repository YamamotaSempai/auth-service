package kz.aa.auth_api.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class LoginDto(
    @JsonProperty(required = true)
    val login: String,
    @JsonProperty(required = true)
    val password: String
) : Serializable

data class RegistrationDto(
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

data class UpdateDto(
    @JsonProperty(required = true)
    @NotBlank
    @NotNull
    var firstName: String,
    @JsonProperty(required = true)
    @NotBlank
    @NotNull
    var lastname: String,
    @JsonProperty(required = true)
    @NotBlank
    @NotNull
    var email: String,
)

