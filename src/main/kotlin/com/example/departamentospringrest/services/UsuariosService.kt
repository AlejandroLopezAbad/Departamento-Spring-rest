package com.example.departamentospringrest.services

import com.example.departamentospringrest.exceptions.UsuariosNotFoundException
import com.example.departamentospringrest.repositories.usuario.UsuariosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuariosService @Autowired constructor(
    private val repo:UsuariosRepository
    //TODO Meter userDetailsService para la seguridad
):UserDetailsService {

    suspend fun findAll()= withContext(Dispatchers.IO){
        return@withContext repo.findAll()
    }

    override fun loadUserByUsername(username: String): UserDetails= runBlocking {
        return@runBlocking repo.findByEmail(username).firstOrNull() //TODO ES LO QUE HAY QUE CAMBIAR PARA QUE FUNCIONE
            ?: throw UsuariosNotFoundException("Usuario no encontrado con username: $username")
    }


    suspend fun loadUsuarioById(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext repo.findById(userId)
    }

    suspend fun loadUserByEmail(email: String) = withContext(Dispatchers.IO) {
        return@withContext repo.findByEmail(email)
    }




}