package ru.sberschool.hystrix

import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /berry/cheri")
    fun getBerry(): Berry
}



