package com.danya.springmvc.controller

import com.danya.springmvc.models.PersonPage
import com.danya.springmvc.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(private val addressBookService: AddressBookService) {

    @PostMapping("/list")
    fun getPage(@RequestBody query: Map<String, String>): ResponseEntity<ConcurrentHashMap<Int, PersonPage>> {
        val searchResult = addressBookService.getPageById(query)

        return ResponseEntity(searchResult, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun addPage(@RequestBody person: PersonPage): ResponseEntity<PersonPage> {
        addressBookService.addPage(person)

        return ResponseEntity(person, HttpStatus.CREATED)
    }

    @GetMapping("/{id}/view")
    fun getPageById(@PathVariable id: String): ResponseEntity<ConcurrentHashMap<Int, PersonPage>> {
        val searchResult = addressBookService.getPageById(mapOf("id" to id))

        return ResponseEntity(searchResult, HttpStatus.OK)
    }

    @PutMapping("/{id}/edit")
    fun editPage(@PathVariable id: String, @RequestBody person: PersonPage): ResponseEntity<PersonPage> {
        val tmp = addressBookService.editPage(id.toInt(), person)

        return ResponseEntity(tmp, HttpStatus.OK)
    }

    @DeleteMapping("/{id}/delete")
    fun deletePage(@PathVariable id: String): ResponseEntity<PersonPage> {
        return ResponseEntity(addressBookService.deletePage(id.toInt()), HttpStatus.OK)
    }
}