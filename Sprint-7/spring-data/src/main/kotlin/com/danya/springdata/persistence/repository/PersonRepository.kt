package com.danya.springdata.persistence.repository

import com.danya.springdata.persistence.enteties.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CrudRepository<Person, Long> {

    fun findByName(name: String): Person

    fun findByBandName(bandName: String): List<Person>

}