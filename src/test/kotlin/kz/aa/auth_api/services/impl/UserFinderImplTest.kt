package kz.aa.auth_api.services.impl

import kz.aa.auth_api.AuthApiApplicationTests
import kz.aa.auth_api.services.UserFinder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserFinderImplTest @Autowired constructor(private val userFinder: UserFinder) : AuthApiApplicationTests() {

    @Test
    fun `getByLogin() find admin user`() {
        assertNotNull(userFinder.getByLogin(login = "admin"))
    }

    @Test
    fun `getByLogin() find not exist user`() {
        assertNull(userFinder.getByLogin(login = "admin1"))
    }

    @Test
    fun `getAll() find all user in pageable`() {
        val page = userFinder.getAll(pageable = Pageable.ofSize(10))
        assertNotNull(page)
        assertTrue(!page.isEmpty)
    }
}