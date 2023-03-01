package com.example.departamentospringrest.services.departamento

import com.example.departamentospringrest.models.Departamento
import java.util.*

interface DepartamentoService {
    suspend fun findAll(): List<Departamento>
    suspend fun findById(id: Long): Departamento?
    suspend fun save(departamento: Departamento): Departamento
    suspend fun update(id: Long, departamento: Departamento): Departamento?
    suspend fun deleteById(id: Long): Departamento?
}