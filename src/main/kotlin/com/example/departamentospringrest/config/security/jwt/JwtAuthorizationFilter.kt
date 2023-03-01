package com.example.departamentospringrest.config.security.jwt

import com.example.departamentospringrest.services.usuarios.UsuariosService
import io.netty.handler.codec.http.HttpHeaderNames
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException


class JwtAuthorizationFilter(
    private val jwtTokenUtil: JwtTokenUtils,
    private val service: UsuariosService,
    authManager: AuthenticationManager,
) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        logger.info { "Filtrando" }
        val header = req.getHeader(HttpHeaderNames.AUTHORIZATION.toString())
        if (header == null || !header.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? = runBlocking {
//        logger.info { "Obteniendo autenticación" }

        if (!jwtTokenUtil.isTokenValid(token)) return@runBlocking null
        // val username = jwtTokenUtil.getUsernameFromJwt(token)
        val userId = jwtTokenUtil.getUserIdFromJwt(token)
        println(userId)
        // val roles = jwtTokenUtil.getRolesFromJwt(token)
        val user = service.loadUsuarioById(userId.toLong())
        println(user)
        return@runBlocking UsernamePasswordAuthenticationToken(
            user,
            null,
            user?.authorities
        )
    }
}