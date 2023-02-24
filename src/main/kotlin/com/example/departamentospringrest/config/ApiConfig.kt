package com.example.departamentospringrest.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {
    companion object{
        @Value("api")
        const val API_PATH="/api"
    }
}