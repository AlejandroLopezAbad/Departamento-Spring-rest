package com.example.departamentospringrest.validators

import com.example.departamentospringrest.dto.DepartamentoCreateDto
import com.example.departamentospringrest.exceptions.DepartamentoBadRequestException

fun DepartamentoCreateDto.validateCreate(): DepartamentoCreateDto{
    if (this.nombre.isBlank())
        throw DepartamentoBadRequestException("El nombre del departamento no puede estar vacío")
    if(this.presupuesto < 0)
        throw DepartamentoBadRequestException("El presupuesto del departamento no puede ser negativo")
    return this
}