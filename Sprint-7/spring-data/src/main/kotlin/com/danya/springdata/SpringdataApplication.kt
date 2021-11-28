package com.danya.springdata

import com.danya.springdata.persistence.enteties.Band
import com.danya.springdata.persistence.enteties.Person
import com.danya.springdata.persistence.repository.PersonRepository
import com.danya.springdata.persistence.enteties.Wife
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class SpringdataApplication(
    private val personRepository: PersonRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val band2 = Band(name = "Red Hot Chili Peppers")
        val band3 = Band(name = "Oasis")

        val person5 = Person(
            name = "Liam Gallagher",
            birthDate = LocalDate.now().minusYears(40),
            band = band3,
            wife = Wife(name = "Nicole Appleton")
        )

        val person6 = Person(
            name = "Noel Gallagher",
            birthDate = LocalDate.now().minusYears(55),
            band = band3,
            wife = Wife(name = "Sara MacDonald")
        )


        personRepository.saveAll(
            listOf(
                person5, person6
            )
        )

        val id: Long = 5 // TODO(Укажи тут id из своей бд)
        println("Найден музыкант с id $id: ${personRepository.findById(id)}")
        println("Найдены музыканты из группы ${band2.name}")
        personRepository.findByBandName(band2.name).forEach{
            println(it)
        }
        val allMusicians = personRepository.findAll()
        println("Все музыканты: $allMusicians")
    }
}

fun main(args: Array<String>) {
    runApplication<SpringdataApplication>(*args)
}
