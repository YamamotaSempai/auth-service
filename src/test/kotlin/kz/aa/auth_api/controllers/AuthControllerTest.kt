package kz.aa.auth_api.controllers

import com.google.gson.Gson
import kz.aa.auth_api.AuthApiApplicationTests
import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.LoginDto
import kz.aa.auth_api.models.dto.RegistrationDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.Charset

@AutoConfigureMockMvc
private open class AuthControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) : AuthApiApplicationTests() {
    private val mediaTypeJson: MediaType = MediaType("application", "json", Charset.forName("UTF-8"))

    @Transactional
    @Test
    fun login() {
        val loginDto = LoginDto(login = "admin", password = "admin")
        val json = Gson().toJson(loginDto, LoginDto::class.java)
        mockMvc.perform(
            post("/auth/login")
                .content(json)
                .accept(mediaTypeJson)
                .contentType(mediaTypeJson)
        )
            .andExpect(status().isOk)
            .andExpect(header().exists("Authorization"))
            .andExpect { it.response.contentAsString.startsWith("{\"id_token\":\"") }
            .andDo(print())
    }

    @Transactional
    @Test
    fun `login() throw exception if credentials are not valid`() {
        val loginDto = LoginDto(login = "admin1", password = "admin1")
        val json = Gson().toJson(loginDto, LoginDto::class.java)
        mockMvc.perform(
            post("/auth/login")
                .content(json)
                .accept(mediaTypeJson)
                .contentType(mediaTypeJson)
        )
            .andExpect(status().isBadRequest)
            .andDo(print())
    }


    @Transactional
    @Test
    fun registration() {
        val registrationDto = RegistrationDto(
            login = "admin1",
            password = "admin1",
            firstName = "admin1",
            lastname = "admin1",
            email = "email@asd.yes"
        )
        val json = Gson().toJson(registrationDto, RegistrationDto::class.java)
        mockMvc.perform(
            post("/auth/registration")
                .content(json)
                .accept(mediaTypeJson)
                .contentType(mediaTypeJson)
        )
            .andExpect(status().isOk)
            .andExpect {
                run {
                    val user = Gson().fromJson(it.response.contentAsString, SecUser::class.java)
                    assertNotNull(user)
                    assertEquals(registrationDto.login, user.login)
                    assertEquals(registrationDto.firstName, user.firstname)
                    assertEquals(registrationDto.lastname, user.lastname)
                    assertEquals(registrationDto.email, user.email)
                }
            }
            .andDo(print())
    }

    @Transactional
    @Test
    fun `registration() throw exception if user already exists`() {
        val registrationDto = RegistrationDto(
            login = "admin",
            password = "admin1",
            firstName = "admin1",
            lastname = "admin1",
            email = "email@asd.yes"
        )
        val json = Gson().toJson(registrationDto, RegistrationDto::class.java)
        mockMvc.perform(
            post("/auth/registration")
                .content(json)
                .accept(mediaTypeJson)
                .contentType(mediaTypeJson)
        )
            .andExpect(status().isBadRequest)
            .andDo(print())
    }
}