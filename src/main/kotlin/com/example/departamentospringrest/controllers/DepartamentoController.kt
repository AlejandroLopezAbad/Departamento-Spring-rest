package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.dto.DepartamentoCreateDto
import com.example.departamentospringrest.exceptions.DepartamentoBadRequestException
import com.example.departamentospringrest.exceptions.DepartamentoNotFoundException
import com.example.departamentospringrest.mappers.toDepartamento
import com.example.departamentospringrest.models.Departamento
import com.example.departamentospringrest.models.Usuario
import com.example.departamentospringrest.services.departamento.DepartamentoService
import com.example.departamentospringrest.services.empleado.EmpleadoService
import com.example.departamentospringrest.validators.validateCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(ApiConfig.API_PATH+"/departamento")
class DepartamentoController
@Autowired constructor(
    private val serDep: DepartamentoService,
    private val empl: EmpleadoService
) {
    @GetMapping("/listDep")
    suspend fun findAll():ResponseEntity<List<Departamento>>{
        val list = serDep.findAll()
        return ResponseEntity.ok(list)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id:Long): ResponseEntity<Departamento>{
        try{
            val find = serDep.findById(id)
            return ResponseEntity.ok(find)
        }catch (e: DepartamentoNotFoundException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,e.message)
        }
    }


    @PostMapping("/save")
    suspend fun save(@RequestBody dto: DepartamentoCreateDto): ResponseEntity<Departamento>{
        try {
            val departamento = dto.validateCreate().toDepartamento()
            serDep.save(departamento)
            return ResponseEntity(departamento, HttpStatus.CREATED)
        }catch (e: DepartamentoBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: Long,
        @RequestBody departamentoDto: DepartamentoCreateDto
    )
            : ResponseEntity<Departamento>{
        try{
            val departamento = departamentoDto.validateCreate().toDepartamento()
            val updated = serDep.update(id, departamento)
            updated?.let {
                return ResponseEntity.ok(updated)
            }?: run{
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el departamento con id: $id")
            }
        }catch (e: DepartamentoBadRequestException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    suspend fun delete(
        @AuthenticationPrincipal user: Usuario,
        @PathVariable id: Long)
            : ResponseEntity<Departamento>{
        val find = serDep.findById(id)
        find?.let {
            val listemp = empl.findEmpleadosByDepartamento(id)//TODO Departamento entero
            if (listemp.isEmpty()){
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }else{
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un departamento que tiene empleados asociados")
            }
        }?: run{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el departamento con el id: $id")
        }
    }


}