package kz.aa.auth_api.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sec_authorities")
data class SecAuthority(
    @Id
    @Column(name = "name", length = 50, nullable = false)
    val name: String
)