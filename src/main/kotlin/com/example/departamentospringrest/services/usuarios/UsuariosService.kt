package com.example.departamentospringrest.services.usuarios

import com.example.departamentospringrest.exceptions.UsuariosBadRequestException
import com.example.departamentospringrest.exceptions.UsuariosNotFoundException
import com.example.departamentospringrest.models.Rol
import com.example.departamentospringrest.models.Usuario
import com.example.departamentospringrest.repositories.usuario.UsuariosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuariosService @Autowired constructor(
    private val repo: UsuariosRepository,
    private val encode: PasswordEncoder
) : UserDetailsService {

    suspend fun findAll() = withContext(Dispatchers.IO) {
        return@withContext repo.findAll()
    }

    override fun loadUserByUsername(username: String): UserDetails = runBlocking {
        return@runBlocking repo.findByEmail(username).firstOrNull() //TODO ES LO QUE HAY QUE CAMBIAR PARA QUE FUNCIONE
            ?: throw UsuariosNotFoundException("Usuario no encontrado con username: $username")
    }


    suspend fun loadUsuarioById(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext repo.findById(userId)
    }

    suspend fun loadUserByEmail(email: String) = withContext(Dispatchers.IO) {
        return@withContext repo.findByEmail(email)
    }

    suspend fun save(usuario: Usuario, isAdmin: Boolean = false): Usuario = withContext(Dispatchers.IO) {

        if (repo.findByEmail(usuario.email)
                .firstOrNull() != null
        ) {
            throw UsuariosBadRequestException("El email ya existe")
        }


        var newUser = usuario.copy(
            password = encode.encode(usuario.password),
            rol = Rol.USER.name,
        )
        if (isAdmin)
            newUser = newUser.copy(
                rol = Rol.ADMIN.name
            )
        try {
            return@withContext repo.save(newUser)
        } catch (e: Exception) {
            throw UsuariosBadRequestException("Error al crear el usuario: El email ya existe")
        }
    }

    suspend fun update(id: Long, usuario: Usuario): Usuario? = withContext(Dispatchers.IO) {

        var userDB = repo.findByEmail(usuario.email).firstOrNull()
        if (userDB != null && userDB.id != usuario.id) {
            throw UsuariosBadRequestException("Ya existe este email")
        }


        var find = repo.findById(id)
        find?.let {
            val updtatedUser = usuario.copy(
                id = it.id
            )
            try {
                return@withContext repo.save(updtatedUser)
            } catch (e: Exception) {
                throw UsuariosBadRequestException("Error al actualizar el usuario: El  email ya existe")
            }
        } ?: run {
            throw UsuariosNotFoundException("No se ha encontrado un usuario con el id: $id")
        }
    }


    suspend fun deleteById(id: Long): Usuario? = withContext(Dispatchers.IO) {
        val find = repo.findById(id)
        find?.let {
            repo.delete(it)
            return@withContext it
        } ?: throw UsuariosNotFoundException("No existe un usuario con el id: $id")
    }


}