package com.danya.springdata.persistence.enteties

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Wife(
    @Id
    @GeneratedValue
    var id: Long = 0,

    var name: String
)
