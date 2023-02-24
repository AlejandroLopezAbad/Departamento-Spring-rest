package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.config.security.jwt.JwtTokenUtils
import com.example.departamentospringrest.dto.UsuarioDTO
import com.example.departamentospringrest.dto.UsuarioLoginDto
import com.example.departamentospringrest.dto.UsuarioWithTokenDto
import com.example.departamentospringrest.mappers.toDto
import com.example.departamentospringrest.models.Usuario
import com.example.departamentospringrest.services.UsuariosService
import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ApiConfig.API_PATH+"/usuarios")
class UsuarioController
@Autowired constructor(
    private val usuarioService:UsuariosService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtils,
){
    @PostMapping("/login")
    fun login(@Valid @RequestBody logingDto: UsuarioLoginDto): ResponseEntity<UsuarioWithTokenDto> {
      //  logger.info { "Login de usuario: ${logingDto.username}" }

        // podríamos hacerlo preguntándole al servicio si existe el usuario
        // pero mejor lo hacemos con el AuthenticationManager que es el que se encarga de ello
        // y nos devuelve el usuario autenticado o null

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                logingDto.email,
                logingDto.password
            )
        )
        // Autenticamos al usuario, si lo es nos lo devuelve
        SecurityContextHolder.getContext().authentication = authentication

        // Devolvemos al usuario autenticado
        val user = authentication.principal as Usuario
        println(user)

        // Generamos el token
        val jwtToken: String = jwtTokenUtil.generateToken(user)
       // logger.info { "Token de usuario: ${jwtToken}" }

        // Devolvemos el usuario con el token
        val userWithToken = UsuarioWithTokenDto(user.toDto(), jwtToken)

        // La respuesta que queremos

        return ResponseEntity.ok(userWithToken)
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    suspend fun list(@AuthenticationPrincipal user: Usuario):ResponseEntity<List<UsuarioDTO>>{//
        val res = usuarioService.findAll().toList().map { it.toDto() }
        return ResponseEntity.ok(res)

    }

}