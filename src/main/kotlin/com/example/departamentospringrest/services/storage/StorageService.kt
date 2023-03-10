package com.example.departamentospringrest.services.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface StorageService {
    fun init()
    fun save(file: MultipartFile, username:String): String
    fun load(filename:String): Resource
}