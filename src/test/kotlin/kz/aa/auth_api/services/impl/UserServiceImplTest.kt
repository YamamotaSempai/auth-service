package kz.aa.auth_api.services.impl

import kz.aa.auth_api.AuthApiApplicationTests
import kz.aa.auth_api.exceptions.UserException
import kz.aa.auth_api.models.dto.RegistrationDto
import kz.aa.auth_api.models.dto.UpdateDto
import kz.aa.auth_api.services.UserFinder
import kz.aa.auth_api.services.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

private open class UserServiceImplTest @Autowired constructor(
    val userService: UserService,
    val userFinder: UserFinder
) :
    AuthApiApplicationTests() {

    @Transactional
    @Test
    fun create() {
        val registrationDto = RegistrationDto(
            login = "admin2",
            password = "admin2",
            lastname = "lastname",
            firstName = "firstname",
            email = "email"
        )

        assertDoesNotThrow { userService.create(registrationDto) }
        val savedUser = userFinder.getByLogin(registrationDto.login)

        assertNotNull(savedUser)
        assertNotNull(savedUser?.id)
        assertNotNull(savedUser?.password)
        assertEquals(true, savedUser?.activated)
        assertEquals(registrationDto.login, savedUser?.login)
        assertEquals(registrationDto.lastname, savedUser?.lastname)
        assertEquals(registrationDto.firstName, savedUser?.firstname)
        assertEquals(registrationDto.email, savedUser?.email)
    }

    @Transactional
    @Test
    fun `create() throw exception if user already exists`() {
        val registrationDto = RegistrationDto(
            login = "admin",
            password = "admin2",
            lastname = "lastname",
            firstName = "firstname",
            email = "email"
        )

        assertThrows(UserException::class.java) { userService.create(registrationDto) }
    }

    @Transactional
    @Test
    fun update() {
        val login = "admin"
        val updateDto = UpdateDto(
            firstName = "lastname",
            lastname = "firstname",
            email = "notemail"
        )
        assertDoesNotThrow { userService.update(login, updateDto) }

        val updatedUser = userFinder.getByLogin(login)

        assertNotNull(updatedUser)
        assertEquals(updateDto.firstName, updatedUser?.firstname)
        assertEquals(updateDto.lastname, updatedUser?.lastname)
        assertEquals(updateDto.email, updatedUser?.email)
    }

    @Transactional
    @Test
    fun `update() throw exception if user not found`() {
        val login = "admin1"
        val updateDto = UpdateDto(
            firstName = "lastname",
            lastname = "firstname",
            email = "notemail"
        )
        assertThrows(UserException::class.java) { userService.update(login, updateDto) }

        assertNull(userFinder.getByLogin(login))
    }

}