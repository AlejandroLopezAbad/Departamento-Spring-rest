package com.example.departamentospringrest.services

import com.example.departamentospringrest.repositories.usuario.UsuariosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuariosService @Autowired constructor(
    private val repo:UsuariosRepository
    //TODO Meter userDetailsService para la seguridad
) {

    suspend fun findAll()= withContext(Dispatchers.IO){
        return@withContext repo.findAll()
    }





}