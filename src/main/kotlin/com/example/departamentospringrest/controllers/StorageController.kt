package com.example.departamentospringrest.controllers

import com.example.departamentospringrest.config.ApiConfig
import com.example.departamentospringrest.exceptions.StorageBadResquestException
import com.example.departamentospringrest.exceptions.StorageException
import com.example.departamentospringrest.models.Usuario
import com.example.departamentospringrest.services.storage.StorageService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDateTime


@RestController
@RequestMapping(ApiConfig.API_PATH+"/storage")
class StorageController
@Autowired constructor(
    private val serviceStorage: StorageService
){

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(
        value = [""],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    suspend fun saveStorage(
        @AuthenticationPrincipal usuario: Usuario,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Map<String, String>> = runBlocking{
        return@runBlocking try {
            if (!file.isEmpty) {
                val fileStored = serviceStorage.save(file, usuario.username)
                val response = mapOf("nombre" to fileStored, "creado" to LocalDateTime.now().toString())
                ResponseEntity(response, HttpStatus.CREATED)
            } else {
                throw StorageBadResquestException("No se puede subir un fichero vacío")
            }
        } catch (e: StorageException) {
            throw StorageBadResquestException(e.message.toString())
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = ["{filename:.+}"])
    @ResponseBody
    fun loadFile(
        @PathVariable filename: String?,
        request: HttpServletRequest
    ): ResponseEntity<Resource> = runBlocking {
        val file: Resource = serviceStorage.load(filename.toString())
        var contentType: String? = null
        contentType = try {
            request.servletContext.getMimeType(file.file.absolutePath)
        } catch (e: IOException) {
            throw StorageBadResquestException("No se puede determinar el tipo del fichero --> $e")
        }
        if (contentType == null) {
            contentType = "application/octet-stream"
        }
        return@runBlocking ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body<Resource?>(file)
    }
}