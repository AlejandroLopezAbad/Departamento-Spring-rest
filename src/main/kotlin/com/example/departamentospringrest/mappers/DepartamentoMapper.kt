package com.example.departamentospringrest.mappers

import com.example.departamentospringrest.dto.DepartamentoCreateDto
import com.example.departamentospringrest.models.Departamento

fun DepartamentoCreateDto.toDepartamento(): Departamento {
    return Departamento(
        id = null,
        nombre = nombre,
        presupuesto = presupuesto
    )
}