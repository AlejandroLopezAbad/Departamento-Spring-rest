package com.example.departamentospringrest.services.empleado


import com.example.departamentospringrest.exceptions.EmpleadosNotFoundException
import com.example.departamentospringrest.models.Departamento
import com.example.departamentospringrest.models.Empleado
import com.example.departamentospringrest.repositories.empleado.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmpleadoServiceImpl
@Autowired constructor(
    private val repo: EmpleadoRepository
) : EmpleadoService {

    override suspend fun findAll(): List<Empleado> {
        return repo.findAll().toList()
    }

    override suspend fun findById(id: Long): Empleado? {
        return repo.findById(id)
    }

    override suspend fun save(empleado: Empleado): Empleado {
        return repo.save(empleado)
    }

    override suspend fun update(id: Long, empleado: Empleado): Empleado? {
        val find = repo.findById(id)
        find?.let {
            val updated = it.copy(
                nombre = empleado.nombre,
                email = empleado.email,
                idDep = empleado.idDep
            )
            repo.save(updated)
            return updated
        } ?: throw EmpleadosNotFoundException("No existe un empleado con el id: $id")
    }

    override suspend fun deleteById(id: Long): Empleado? {
        val find = repo.findById(id)
        find?.let {
            repo.delete(it)
            return it
        }?: throw EmpleadosNotFoundException("No existe un empleado con el id: $id")
    }

  override  suspend fun findEmpleadosByDepartamento(iDdep: Long): List<Empleado> {
       val lista= repo.findEmpleadoByIdDep(iDdep)

        return lista.toList()
    }
}

