package com.example.departamentospringrest.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Table("Usuarios")
data class Usuario(
    @Id
    val id :Long?=null,
    val email: String,
    val nombre: String,
    @get:JvmName("userPassword")
    val password: String,
    val rol: String = Rol.USER.name //Asi lo tiene el profe
): UserDetails {
    enum class Rol{
        ADMIN,USER
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return rol.split(",").map { SimpleGrantedAuthority("ROLE_${it.trim()}") }.toMutableList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return nombre
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }


    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}