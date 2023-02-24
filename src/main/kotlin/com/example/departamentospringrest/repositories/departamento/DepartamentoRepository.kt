package com.example.departamentospringrest.repositories.departamento

import com.example.departamentospringrest.models.Departamento
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DepartamentoRepository  : CoroutineCrudRepository<Departamento, Long> {
}