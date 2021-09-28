package kz.aa.auth_api.controllers

import kz.aa.auth_api.models.SecUser
import kz.aa.auth_api.models.dto.UpdateDto
import kz.aa.auth_api.services.UserFinder
import kz.aa.auth_api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
    val userFinder: UserFinder,
    val userService: UserService
) {

    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<SecUser>> {
        return ResponseEntity.ok(userFinder.getAll(pageable))
    }

    @PutMapping
    fun update(
        @RequestBody updateDto: @Valid UpdateDto,
        principal: Principal
    ): ResponseEntity<SecUser> {
        return ResponseEntity.ok(userService.update(principal.name, updateDto))
    }
}