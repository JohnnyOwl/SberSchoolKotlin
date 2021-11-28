package com.example.demo.controller

import com.example.demo.persistance.Entity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller{

    @GetMapping("hi")
    fun sayHello(): String{
        return "Hello, Kotlin!"
    }

    @GetMapping("main")
    fun showEntity(): String{
        val entity = Entity(0, "John")
        return "${entity.name}"
    }
}