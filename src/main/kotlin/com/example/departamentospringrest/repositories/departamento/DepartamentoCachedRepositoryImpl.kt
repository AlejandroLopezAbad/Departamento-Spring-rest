package com.example.departamentospringrest.repositories.departamento

import com.example.departamentospringrest.models.Departamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DepartamentoCachedRepositoryImpl @Autowired constructor(
    private val repo: DepartamentoRepository
) : DepartamentoCachedRepository{


    override suspend fun findAll(): Flow<Departamento>  = withContext(Dispatchers.IO) {

        return@withContext repo.findAll()
    }
    @Cacheable("Departamento")
    override suspend fun findById(id: Long): Departamento?  = withContext(Dispatchers.IO){

        return@withContext repo.findById(id)


    }

    @CachePut("Departamento")
    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        return@withContext repo.save(entity)
    }
    @CachePut("Departamento")
    override suspend fun update(id: Long, entity: Departamento): Departamento?  = withContext(Dispatchers.IO){
        var entityDB = repo.findById(id)

        if (entityDB != null) {
            entityDB = entity.copy(id = id)
            entityDB = repo.save(entityDB)
        }

        return@withContext entityDB
    }
    @CacheEvict("Departamento")
    override suspend fun deleteById(id: Long): Departamento?  = withContext(Dispatchers.IO){
        val entityDB = repo.findById(id)

        if (entityDB != null) {
            repo.delete(entityDB)
        }

        return@withContext entityDB
    }
}