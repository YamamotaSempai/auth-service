package kz.aa.auth_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableJpaRepositories
@SpringBootApplication
@EnableEurekaClient
class AuthApiApplication {

    // Configure BCrypt password encoder =====================================================================
    @Bean("bCryptPasswordEncoder")
    fun bCryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}

fun main(args: Array<String>) {
    runApplication<AuthApiApplication>(*args)
}
