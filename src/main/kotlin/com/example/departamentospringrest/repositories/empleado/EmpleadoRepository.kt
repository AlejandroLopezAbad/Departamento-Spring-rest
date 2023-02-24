package com.example.departamentospringrest.repositories.empleado


import com.example.departamentospringrest.models.Empleado
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EmpleadoRepository: CoroutineCrudRepository<Empleado, Long> {
}