package com.example.departamentospringrest.repositories.usuario

import com.example.departamentospringrest.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuariosRepository: CoroutineCrudRepository<Usuario, Long> {

    fun findByEmail(email:String): Flow<Usuario>
    fun findByNombre(nombre:String):Flow<Usuario>

}