package kz.aa.auth_api.models

import lombok.ToString
import org.hibernate.annotations.BatchSize
import javax.persistence.*

@Entity
@Table(name = "sec_roles")
data class SecRole(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    val id: Long,

    @ManyToMany
    @JoinTable(
        name = "sec_role_authorities",
        joinColumns = [JoinColumn(name = "sec_role_id", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "NAME")]
    )
    @BatchSize(size = 20)
    @ToString.Exclude
    val authorities: Set<SecAuthority> = setOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SecRole

        if (id != other.id) return false
        if (authorities != other.authorities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + authorities.hashCode()
        return result
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }

}