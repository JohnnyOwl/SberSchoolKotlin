package ru.sber.functional

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StudentsGroupTest {

    @Test
    fun `filterByPredicate should return students who have a middle name`() {
        val expected = listOf(
            Student(firstName = "John", middleName = "Winston", lastName = "Lennon", age = 20, averageRate = 4.2),
            Student(
                firstName = "George", middleName = "Henry", lastName = "Martin", age = 39, averageRate = 5.0,
                city = "Wiltshire", specialization = "Producer", prevEducation = "Arranger"
            )
        )

        val actual = StudentsGroup().filterByPredicate { Student -> Student.middleName != "Среднее имя отсутствует" }

        assertEquals(expected, actual)
    }

    @Test
    fun `filterByPredicate should return students when average rate more than 4,6`() {
        val expected = listOf(
            Student(firstName = "Paul", lastName = "McCartney", averageRate = 4.8),
            Student(
                firstName = "George", middleName = "Henry", lastName = "Martin", age = 39, averageRate = 5.0,
                city = "Wiltshire", specialization = "Producer", prevEducation = "Arranger"
            )
        )

        val actual = StudentsGroup().filterByPredicate { Student -> Student.averageRate >= 4.6 }

        assertEquals(expected, actual)
    }

    @Test
    fun `filterByPredicate should return students who do not have a city`() {
        val expected = listOf(
            Student(firstName = "Paul", lastName = "McCartney", averageRate = 4.8),
            Student(firstName = "George", lastName = "Harrison", specialization = "Electrician", averageRate = 4.5),
            Student(firstName = "John", middleName = "Winston", lastName = "Lennon", age = 20, averageRate = 4.2),
        )

        val actual = StudentsGroup().filterByPredicate { Student -> Student.city >= "Город отсутствует" }

        assertEquals(expected, actual)
    }

    @Test
    fun `filterByPredicate should return students who younger than 30 and not default`() {
        val expected = listOf(
            Student(firstName = "Paul", lastName = "McCartney", averageRate = 4.8),
            Student(firstName = "George", lastName = "Harrison", specialization = "Electrician", averageRate = 4.5),
            Student(firstName = "Ringo", lastName = "Starr", city = "Liverpool", averageRate = 4.1),
            Student(firstName = "John", middleName = "Winston", lastName = "Lennon", age = 20, averageRate = 4.2)
        )

        val actual = StudentsGroup().filterByPredicate { Student -> Student.age <= 30 }

        assertEquals(expected, actual)
    }
}