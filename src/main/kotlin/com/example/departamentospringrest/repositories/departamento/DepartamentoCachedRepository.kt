package com.example.departamentospringrest.repositories.departamento

import com.example.departamentospringrest.models.Departamento
import kotlinx.coroutines.flow.Flow

interface DepartamentoCachedRepository {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: Long): Departamento?
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(id: Long, entity: Departamento): Departamento?
    suspend fun deleteById(id: Long): Departamento?
}