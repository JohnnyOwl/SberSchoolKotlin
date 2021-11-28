package com.danya.springmvc.filter

import java.time.Instant
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletContext
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
private class AuthenticationFilter : HttpFilter() {

    private lateinit var context: ServletContext

    override fun init(filterConfig: FilterConfig) {
        context = filterConfig.servletContext
        context.log("Authentication filter is initialized")
    }

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val cookies = request!!.cookies

        if (cookies == null) {
            context.log("No Cookie, Unknown Authorization Try")
            response!!.sendRedirect("/login")
        } else {
            val currentTime = Instant.now().toString()
            var logMessage = ""

            for (cookie in cookies) {
                if (cookie.name == "auth") {
                    if (currentTime > cookie.value) {
                        logMessage = "Valid Cookie"
                        context.log(logMessage)
                        chain!!.doFilter(request, response)
                    } else {
                        logMessage = "CookieERROR: Value - ${cookie.value}: Current - $currentTime"
                        context.log(logMessage)
                        response!!.sendRedirect("/login")
                    }
                }
            }

            if (logMessage.isEmpty()) {
                context.log("Not Found Cookie")
                response!!.sendRedirect("/login")
            }
        }
    }
}