package com.danya.springdata.persistence.enteties

import javax.persistence.*

@Entity
class Band(
    @Id
    @GeneratedValue
    var id: Long = 0,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var person: MutableList<Person> = mutableListOf(),

    var name: String
)
