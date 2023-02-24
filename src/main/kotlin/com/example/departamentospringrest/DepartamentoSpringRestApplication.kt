package com.example.departamentospringrest

import com.example.departamentospringrest.repositories.usuario.UsuariosRepository
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DepartamentoSpringRestApplication


fun main(args: Array<String>) {
    runApplication<DepartamentoSpringRestApplication>(*args)
}
