package com.example.departamentospringrest.mappers

import com.example.departamentospringrest.dto.UsuarioCreateDto
import com.example.departamentospringrest.dto.UsuarioDTO
import com.example.departamentospringrest.models.Usuario

fun Usuario.toDto(): UsuarioDTO {
    return UsuarioDTO(
        id = this.id.toString(),
        email = this.email,
        nombre = this.nombre,
        rol = this.rol.split(",").map { it.trim() }.toSet(),
    )
}

fun UsuarioCreateDto.toModel(): Usuario {

    return Usuario(
        email = this.email,
        nombre = this.nombre,
        password = this.password,
        rol = this.rol.joinToString(", ") { it.uppercase().trim() },
        )

}