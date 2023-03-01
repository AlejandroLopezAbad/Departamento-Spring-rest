package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.config.security.jwt.JwtTokenUtils
import com.example.departamentospringrest.dto.UsuarioCreateDto
import com.example.departamentospringrest.dto.UsuarioDTO
import com.example.departamentospringrest.dto.UsuarioLoginDto
import com.example.departamentospringrest.dto.UsuarioWithTokenDto
import com.example.departamentospringrest.exceptions.UsuariosBadRequestException
import com.example.departamentospringrest.mappers.toDto
import com.example.departamentospringrest.mappers.toModel
import com.example.departamentospringrest.models.Usuario
import com.example.departamentospringrest.services.usuarios.UsuariosService
import com.example.departamentospringrest.validators.validate
import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(ApiConfig.API_PATH+"/usuarios")
class UsuarioController
@Autowired constructor(
    private val usuarioService: UsuariosService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtils,
){
    @PostMapping("/login")
    fun login(@Valid @RequestBody logingDto: UsuarioLoginDto): ResponseEntity<UsuarioWithTokenDto> {


        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                logingDto.email,
                logingDto.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication


        val user = authentication.principal as Usuario
        println(user)


        val jwtToken: String = jwtTokenUtil.generateToken(user)

        val userWithToken = UsuarioWithTokenDto(user.toDto(), jwtToken)



        return ResponseEntity.ok(userWithToken)
    }

    @PostMapping("/register")
    suspend fun register(@RequestBody usuarioDto: UsuarioCreateDto): ResponseEntity<UsuarioWithTokenDto>{
        try{
            val user = usuarioDto.validate().toModel()
            val save = usuarioService.save(user, user.rol.contains("ADMIN"))
            val token = jwtTokenUtil.generateToken(save)
            return ResponseEntity(UsuarioWithTokenDto(save.toDto(), token), HttpStatus.CREATED)
        }catch (e: UsuariosBadRequestException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    suspend fun list(@AuthenticationPrincipal user: Usuario):ResponseEntity<List<UsuarioDTO>>{//
        val res = usuarioService.findAll().toList().map { it.toDto() }
        return ResponseEntity.ok(res)

    }

    @GetMapping("/me")
    suspend fun me(@AuthenticationPrincipal user: Usuario): ResponseEntity<UsuarioDTO>{
        return ResponseEntity.ok(user.toDto())
    }

}