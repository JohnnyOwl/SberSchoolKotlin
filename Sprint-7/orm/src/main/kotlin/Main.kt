import enteties.Band
import enteties.Person
import enteties.Wife
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.time.LocalDate

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Person::class.java)
        .addAnnotatedClass(Band::class.java)
        .addAnnotatedClass(Wife::class.java)
        .buildSessionFactory()

    sessionFactory.use {
        val dao = PersonDAO(it)
        val band1 = Band(name = "The Beatles")
        val band2 = Band(name = "Red Hot Chili Peppers")

        val person1 = Person(
            name = "John Lennon",
            birthDate = LocalDate.now().minusYears(80),
            band = band1,
            wife = Wife(name = "Yoko Ono")
        )

        val person2 = Person(
            name = "Paul McCartney",
            birthDate = LocalDate.now().minusYears(79),
            band = band1,
            wife = Wife(name = "Nancy Shevell")
        )

        val person3 = Person(
            name = "Anthony Kiedis",
            birthDate = LocalDate.now().minusYears(60),
            band = band2,
            wife = Wife(name = "Yohanna Logan")
        )

        val person4 = Person(
            name = "Flea",
            birthDate = LocalDate.now().minusYears(59),
            band = band2,
            wife = Wife(name = "Melody Ehsani")
        )

        dao.save(person1)
        dao.save(person2)
        dao.save(person3)
        dao.save(person4)

        var found = dao.find(person1.id)
        println("Найден музыкант: $found\n")

        found = dao.find(person2.name)
        println("Найдем музыкант: $found\n")

        val allPersons = dao.findAll()
        println("Все музыканты: $allPersons")

    }
}

class PersonDAO(
    private val sessionFactory: SessionFactory
) {
    fun save(person: Person) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(person)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Person? {
        val result: Person?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Person::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun find(name: String): Person? {
        val result: Person?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.byNaturalId(Person::class.java)
                    .using("name", name)
                    .loadOptional()
                    .orElse(null)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Person>{
        val result: List<Person>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Person")
                .list() as List<Person>
        }
        return result
    }
}