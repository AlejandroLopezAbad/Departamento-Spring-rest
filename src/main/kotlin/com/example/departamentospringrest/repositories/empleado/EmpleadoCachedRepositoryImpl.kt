package com.example.departamentospringrest.repositories.empleado

import com.example.departamentospringrest.models.Empleado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository


@Repository
class EmpleadoCachedRepositoryImpl @Autowired constructor(
    private val repo :EmpleadoRepository
) :EmpleadoCachedRepository{


    override suspend fun findAll(): Flow<Empleado>  = withContext(Dispatchers.IO) {

        return@withContext repo.findAll()
    }
    @Cacheable("Empleados")
    override suspend fun findById(id: Long): Empleado?  = withContext(Dispatchers.IO){

        return@withContext repo.findById(id)
    }
    @CachePut("Empleados")
    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {

        return@withContext repo.save(entity)
    }
    @CachePut("Empleados")
    override suspend fun update(id: Long, entity: Empleado): Empleado? = withContext(Dispatchers.IO) {

        var entityDB = repo.findById(id)

        if (entityDB != null) {
            entityDB = entity.copy(id = id)
            entityDB = repo.save(entityDB)
        }

        return@withContext entityDB
    }
    @CacheEvict("Empleados")
    override suspend fun deleteById(id: Long): Empleado? = withContext(Dispatchers.IO) {

        val entityDB = repo.findById(id)

        if (entityDB != null) {
            repo.delete(entityDB)
        }

        return@withContext entityDB
    }
}