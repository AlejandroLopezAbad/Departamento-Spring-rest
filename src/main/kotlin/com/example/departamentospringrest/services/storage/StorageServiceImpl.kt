package com.example.departamentospringrest.services.storage

import com.example.departamentospringrest.exceptions.StorageBadResquestException
import com.example.departamentospringrest.exceptions.StorageFileNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class StorageServiceImpl(
    @Value("\${upload.location}")
    private var path: String
): StorageService {

    private val rootlocation: Path

    init {
        rootlocation = Paths.get(path)
        this.init()
    }

    override fun init() {
        try {
            if(!Files.exists(rootlocation)){
                Files.createDirectory(rootlocation)
            }
        }catch (e: IOException) {
            throw StorageBadResquestException("Problemas para inilicializar el storage")
        }
    }

    override fun save(file: MultipartFile, username: String): String {
        val filename = StringUtils.cleanPath(file.originalFilename.toString())
        val extension = StringUtils.getFilenameExtension(filename).toString()
        val guardar ="$username.$extension"
        try {
            if (file.isEmpty) {
                throw StorageBadResquestException("No se puede guardar en un  fichero vacío $filename")
            }
            if (filename.contains("..")) {
                throw StorageBadResquestException("No se puede almacenar un fichero fuera del path permitido $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, rootlocation.resolve(guardar),
                    StandardCopyOption.REPLACE_EXISTING
                )
                return guardar
            }
        } catch (e: IOException) {
            throw StorageBadResquestException("Fallo al almacenar fichero $filename -> $e")
        }
    }

    override fun load(filename: String): Resource {
        return try {
            val fichero = rootlocation.resolve(filename)
            val resource = UrlResource(fichero.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                    "No se puede leer fichero: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("No se puede leer fichero: $filename --> $e")
        }
    }
}