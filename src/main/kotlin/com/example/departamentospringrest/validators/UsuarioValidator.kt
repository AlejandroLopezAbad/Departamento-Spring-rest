package com.example.departamentospringrest.validators

import com.example.departamentospringrest.dto.UsuarioCreateDto
import com.example.departamentospringrest.exceptions.UsuariosBadRequestException


fun UsuarioCreateDto.validate(): UsuarioCreateDto {
    if (this.nombre.isBlank()) {
        throw UsuariosBadRequestException("El nombre no puede estar vacio")
    } else if (this.email.isBlank() || !this.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))) {
        throw UsuariosBadRequestException("El email no puede estar vacío o no tiene el formato correcto ")
    }else if (this.password.isBlank()|| this.password.length<5){
        throw UsuariosBadRequestException("El password no puede estar vacío o ser menor de 5 caracteres")
    }
    return this
}



