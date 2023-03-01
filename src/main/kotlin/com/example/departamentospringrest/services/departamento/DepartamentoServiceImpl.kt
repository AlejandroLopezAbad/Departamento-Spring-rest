package com.example.departamentospringrest.services.departamento

import com.example.departamentospringrest.exceptions.DepartamentoNotFoundException
import com.example.departamentospringrest.models.Departamento
import com.example.departamentospringrest.repositories.departamento.DepartamentoCachedRepository
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DepartamentoServiceImpl
@Autowired constructor(
    private val repo: DepartamentoCachedRepository
): DepartamentoService {

    override suspend fun findAll(): List<Departamento> {
        return repo.findAll().toList()
    }

    override suspend fun findById(id: Long): Departamento? {
        return repo.findById(id)

    }


    override suspend fun save(departamento: Departamento): Departamento {
        return repo.save(departamento)
    }

    override suspend fun update(id: Long, departamento: Departamento): Departamento? {
        return repo.update(id, departamento)

    }

    override suspend fun deleteById(id: Long): Departamento? {
        return repo.deleteById(id)
            ?: throw DepartamentoNotFoundException("No se ha encontrado un departamento con el id: $id")
    }
}
