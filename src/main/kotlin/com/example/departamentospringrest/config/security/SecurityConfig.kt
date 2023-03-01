package com.example.departamentospringrest.config.security

import com.example.departamentospringrest.config.security.jwt.JwtAuthenticationFilter
import com.example.departamentospringrest.config.security.jwt.JwtAuthorizationFilter
import com.example.departamentospringrest.config.security.jwt.JwtTokenUtils
import com.example.departamentospringrest.services.usuarios.UsuariosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig @Autowired constructor(
    private val usuarioService: UsuariosService,
    private val jwtTokenUtils: JwtTokenUtils
) {

    @Bean
    fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(usuarioService)
        return authenticationManagerBuilder.build()
    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        val authenticationManager = authManager(http)

        http
            .csrf().disable()
            .exceptionHandling()
            .and()

            .authenticationManager(authenticationManager)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/error/**").permitAll()
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("usuarios/login").permitAll()
            .requestMatchers("/departamento").permitAll()
            .requestMatchers("/empleados").permitAll()

                //aqui van los otros endpoints
            .anyRequest().authenticated()
            .and()
            .addFilter(JwtAuthenticationFilter(jwtTokenUtils, authenticationManager))
            .addFilter(JwtAuthorizationFilter(jwtTokenUtils, usuarioService, authenticationManager))

            return http.build()


    }


}