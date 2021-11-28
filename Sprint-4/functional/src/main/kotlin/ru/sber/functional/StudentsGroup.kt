package ru.sber.functional

class StudentsGroup {
    private var students: List<Student> = listOf(
        Student(firstName = "Paul", lastName = "McCartney", averageRate = 4.8),
        Student(firstName = "George", lastName = "Harrison", specialization = "Electrician", averageRate = 4.5),
        Student(firstName = "Ringo", lastName = "Starr", city = "Liverpool", averageRate = 4.1),
        Student(firstName = "John", middleName = "Winston", lastName = "Lennon", age = 20, averageRate = 4.2),
        Student(firstName = "George", middleName = "Henry", lastName = "Martin", age = 39, averageRate = 5.0,
            city = "Wiltshire", specialization = "Producer", prevEducation = "Arranger")
    )

    fun filterByPredicate(predicate: (Student) -> Boolean): List<Student> {
        return students.filter(predicate)
    }
}
