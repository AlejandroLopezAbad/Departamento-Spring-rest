package com.example.departamentospringrest.exceptions

sealed class DepartamentoException(message: String) : RuntimeException(message)
class DepartamentoNotFoundException(message: String) : DepartamentoException(message)
class DepartamentoBadRequestException(message: String) : DepartamentoException(message)