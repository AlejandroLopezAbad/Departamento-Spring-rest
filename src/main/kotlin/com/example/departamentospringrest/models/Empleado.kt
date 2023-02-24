package com.example.departamentospringrest.models

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("Empleado")
data class Empleado(
    val id: Long?=null,
    val nombre: String,
    val email: String,
    val avatar: String,
    val departamento: Departamento
) {
}