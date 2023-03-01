package com.example.departamentospringrest.dto

import com.example.departamentospringrest.models.Rol
import com.example.departamentospringrest.models.Usuario


data class UsuarioDTO(
    val id: String,
    val nombre: String,
    val email: String,
    val rol: Set<String> = setOf(Rol.USER.name),
)


data class UsuarioUpdateDto(
    val email: String,
    val nombre: String,
)


data class UsuarioCreateDto(
    val email: String,
    val nombre: String,
    val password: String,
    val rol: Set<String> = setOf(Rol.USER.name),
)

data class UsuarioLoginDto(
    val email: String,
    val password: String
)

data class UsuarioWithTokenDto(
    val usuario: UsuarioDTO,
    val token: String
)