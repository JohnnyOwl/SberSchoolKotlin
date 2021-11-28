package com.danya.springdata.persistence.enteties

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Person(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @NaturalId
    var name: String,

    @Column(name = "birthdate")
    var birthDate: LocalDate,

    @OneToOne(cascade = [CascadeType.ALL])
    var wife: Wife,

    @ManyToOne(cascade = [CascadeType.ALL])
    var band: Band,

    @CreationTimestamp
    @Column(name="createdtime")
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    @Column(name="updatedtime")
    var updatedTime: LocalDateTime? = null

) {
    override fun toString(): String {
        return "Person(id='$id', name='$name', band='${band.name}', wife='${wife.name}', birthDate='$birthDate')"
    }
}

