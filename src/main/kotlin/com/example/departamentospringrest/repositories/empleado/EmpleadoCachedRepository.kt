package com.example.departamentospringrest.repositories.empleado

import com.example.departamentospringrest.models.Empleado
import kotlinx.coroutines.flow.Flow

interface EmpleadoCachedRepository {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: Long): Empleado?
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(id: Long, entity: Empleado): Empleado?
    suspend fun deleteById(id: Long): Empleado?

}