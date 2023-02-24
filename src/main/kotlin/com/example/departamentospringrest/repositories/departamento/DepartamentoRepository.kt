package com.example.departamentospringrest.repositories.departamento

import com.example.departamentospringrest.models.Departamento
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartamentoRepository  : CoroutineCrudRepository<Departamento, Long> {
}