package kz.aa.auth_api.models

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "sec_users")
data class SecUser(
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    var id: Long = -1,

    @Column(name = "login", length = 50, unique = true)
    var login: @NotNull @Size(min = 4, max = 50) String,

    @JsonIgnore
    @Column(name = "password", length = 100)
    var password: @NotNull @Size(min = 4, max = 100) String,

    @Column(name = "firstname", length = 50)
    var firstname: @NotNull @Size(min = 4, max = 50) String,

    @Column(name = "lastname", length = 50)
    var lastname: @NotNull @Size(min = 4, max = 50) String,

    @Email
    @Column(name = "email", length = 50)
    var email: @NotNull @Size(min = 4, max = 50) String,

    @JsonIgnore
    @Column(name = "activated")
    var activated: Boolean = false,

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    @ToString.Exclude
    var roles: Set<SecRole> = setOf(),
) {


    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , login = $login , password = $password , firstname = $firstname , lastname = $lastname , email = $email , activated = $activated )"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SecUser

        if (id != other.id) return false
        if (login != other.login) return false
        if (password != other.password) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (email != other.email) return false
        if (activated != other.activated) return false
        if (roles != other.roles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + activated.hashCode()
        result = 31 * result + roles.hashCode()
        return result
    }

    fun modify(firstname: String?, lastname: String?, email: String?): SecUser {
        if (firstname != null) {
            this.firstname = firstname
        }
        if (lastname != null) {
            this.lastname = lastname
        }
        if (email != null) {
            this.email = email
        }
        return this
    }
}
