package com.example.departamentospringrest.repositories.empleado


import com.example.departamentospringrest.models.Departamento
import com.example.departamentospringrest.models.Empleado
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpleadoRepository: CoroutineCrudRepository<Empleado, Long> {
    fun findEmpleadoByIdDep(long:Long):Flow<Empleado>
}