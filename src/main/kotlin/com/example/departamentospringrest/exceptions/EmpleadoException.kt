package com.example.departamentospringrest.exceptions

sealed class EmpleadosException(message: String) : RuntimeException(message)
class EmpleadosNotFoundException(message: String) : EmpleadosException(message)
class EmpleadosBadRequestException(message: String) : EmpleadosException(message)