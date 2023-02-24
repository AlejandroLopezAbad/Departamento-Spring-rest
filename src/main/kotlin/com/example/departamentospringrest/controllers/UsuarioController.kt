package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.dto.UsuarioDTO
import com.example.departamentospringrest.mappers.toDto
import com.example.departamentospringrest.services.UsuariosService
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ApiConfig.API_PATH+"/usuarios")
class UsuarioController
@Autowired constructor(
    private val usuarioService:UsuariosService
){

    @GetMapping("/list")
    suspend fun list():ResponseEntity<List<UsuarioDTO>>{
        val res = usuarioService.findAll().toList().map { it.toDto() }
        return ResponseEntity.ok(res)

    }

}