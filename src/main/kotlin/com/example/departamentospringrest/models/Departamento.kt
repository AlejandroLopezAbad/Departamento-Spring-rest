package com.example.departamentospringrest.models

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("Departamento")
data class Departamento(
    val id:Long?=null,
    val nombre:String,
    val presupuesto:Float
) {
}