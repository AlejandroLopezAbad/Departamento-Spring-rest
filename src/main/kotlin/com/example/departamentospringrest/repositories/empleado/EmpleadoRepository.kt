package com.example.departamentospringrest.repositories.empleado


import com.example.departamentospringrest.models.Empleado
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpleadoRepository: CoroutineCrudRepository<Empleado, Long> {
}