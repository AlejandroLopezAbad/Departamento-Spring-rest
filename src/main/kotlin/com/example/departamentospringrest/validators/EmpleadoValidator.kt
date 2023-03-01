package com.example.departamentospringrest.validators

import com.example.departamentospringrest.dto.EmpleadoCreateDto
import com.example.departamentospringrest.exceptions.EmpleadosBadRequestException

fun EmpleadoCreateDto.validarCreate():EmpleadoCreateDto{
    if(this.nombre.isBlank())
        throw EmpleadosBadRequestException("El nombre no puede estar vacío")

    if(this.email.isBlank()||!this.email.matches(Regex("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$")))
        throw EmpleadosBadRequestException("El email tiene un formato incorrecto")

    return this
}