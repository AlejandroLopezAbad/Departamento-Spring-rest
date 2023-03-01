package com.example.departamentospringrest.mappers

import com.example.departamentospringrest.dto.EmpleadoCreateDto
import com.example.departamentospringrest.exceptions.EmpleadosBadRequestException
import com.example.departamentospringrest.models.Empleado


fun EmpleadoCreateDto.toEmpleado(): Empleado {
    try {
        return Empleado(
            id=null,
            nombre = nombre,
            email = email,
            idDep = departamento.toLong()
        )
    }catch (e : IllegalArgumentException){
        throw EmpleadosBadRequestException("UUID de departamento incorrecto")
    }
}