package enteties

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

    var birthDate: LocalDate,

    @OneToOne(cascade = [CascadeType.ALL])
    var wife: Wife,

    @ManyToOne(cascade = [CascadeType.ALL])
    var band: Band,

    @CreationTimestamp
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedTime: LocalDateTime? = null

) {
    override fun toString(): String {
        return "Person(id='$id', name='$name', band='${band.name}', wife='${wife.name}', birthDate='$birthDate')"
    }
}