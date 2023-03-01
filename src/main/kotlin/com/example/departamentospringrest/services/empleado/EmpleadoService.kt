package com.example.departamentospringrest.services.empleado

import com.example.departamentospringrest.models.Empleado
import java.util.*

interface EmpleadoService {
    suspend fun findAll(): List<Empleado>
    suspend fun findById(id: Long): Empleado?
    suspend fun save(empleado: Empleado): Empleado
    suspend fun update(id: Long, empleado: Empleado): Empleado?
    suspend fun deleteById(id: Long): Empleado?
    suspend fun findEmpleadosByDepartamento(iDdep: Long): List<Empleado>
}