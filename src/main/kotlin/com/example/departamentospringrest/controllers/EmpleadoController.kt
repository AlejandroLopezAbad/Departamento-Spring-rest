package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.dto.EmpleadoCreateDto
import com.example.departamentospringrest.exceptions.EmpleadosBadRequestException
import com.example.departamentospringrest.exceptions.EmpleadosNotFoundException
import com.example.departamentospringrest.mappers.toEmpleado
import com.example.departamentospringrest.models.Empleado
import com.example.departamentospringrest.services.departamento.DepartamentoService
import com.example.departamentospringrest.services.empleado.EmpleadoService
import com.example.departamentospringrest.validators.validarCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping(ApiConfig.API_PATH+"/empleados")
class EmpleadoController
@Autowired constructor(
    private val serDep: DepartamentoService,
    private val serEmpl: EmpleadoService
) {

    @GetMapping("/list")
    suspend fun findAll(): ResponseEntity<List<Empleado>> {
        val lista = serEmpl.findAll()
        return ResponseEntity.ok(lista)
    }


    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: Long): ResponseEntity<Empleado> {
        try {
            val find = serEmpl.findById(id)
            return ResponseEntity.ok(find)
        }catch (e : EmpleadosNotFoundException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }


    @PostMapping("/save")
    suspend fun save(@RequestBody dto: EmpleadoCreateDto): ResponseEntity<Empleado> {
        try{
            val post = dto.validarCreate().toEmpleado()
            val findDpt = serDep.findById(post.idDep!!)
            findDpt?.let {
                val save = serEmpl.save(post)
                return ResponseEntity.ok(save)
            }?: run{
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento con ID ${post.idDep} no se ha encontrado")
            }
        }catch (e: EmpleadosBadRequestException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PutMapping("/{id}")
    suspend fun update(
        @RequestBody dto: EmpleadoCreateDto,
        @PathVariable id: Long
    ): ResponseEntity<Empleado> {
        try {
            val empl = dto.validarCreate().toEmpleado()
            val find = serEmpl.findById(id)
            find?.let {
                val findDpt = serDep.findById(it.idDep!!)
                findDpt?.let {
                    empl.id = find.id
                    val update = serEmpl.update(id, empl)
                    return ResponseEntity.ok(update)
                }?: run{
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, " El Departamento con ID ${find.idDep} no se ha encontrado")
                }
            } ?: run {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado ningun empleado con el id: $id")
            }
        }catch (e: EmpleadosBadRequestException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id:Long): ResponseEntity<Empleado> {
        val find = serEmpl.findById(id)
        find?.let{
            serEmpl.deleteById(it.id!!)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }?:run{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el empleado para borrar  con el id: $id")
        }
    }
}