package com.example.departamentospringrest

import com.example.departamentospringrest.models.Departamento
import com.example.departamentospringrest.repositories.usuario.UsuariosRepository
import com.example.departamentospringrest.services.departamento.DepartamentoService
import com.example.departamentospringrest.services.empleado.EmpleadoService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DepartamentoSpringRestApplication/* : CommandLineRunner {
    @Autowired
    lateinit var service: DepartamentoService
    lateinit var serviceEmpl: EmpleadoService

    override fun run(vararg args: String?) = runBlocking {
        /*
        // vamos a probar los metodos de busqueda
     //   val usuario = service.loadUserById(1)!!
        println(usuario.toString())
        println(usuario.rol)
        val usuario2 = service.loadUserByUsername("pepe")
        println(usuario2.authorities)
        service.findAll().toList().forEach { println(it) }
        val userCreateDto = UsuarioCreateDto(
            nombre = "test",
            email = "test@test.com",
            username = "test",
            password = "test"

        )*/





    }
}*/


fun main(args: Array<String>) {
    runApplication<DepartamentoSpringRestApplication>(*args)
}
