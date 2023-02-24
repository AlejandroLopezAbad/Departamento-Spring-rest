package com.example.departamentospringrest.models

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("Usuarios")
data class Usuario(
    val id :Long?=null,
    val email: String,
    val nombre: String,
    val password: String,
    val rol: String = Rol.USER.name //Asi lo tiene el profe
) {
    enum class Rol{
        ADMIN,USER
    }
}