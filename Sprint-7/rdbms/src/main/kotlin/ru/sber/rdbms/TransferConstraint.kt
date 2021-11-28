package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferConstraint {
    private val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/db",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->
            try {
                var prepareStatement = conn.prepareStatement("UPDATE account1 SET amount = amount - ? WHERE id = ?")
                prepareStatement.setLong(1, amount)
                prepareStatement.setLong(2, accountId1)

                prepareStatement.executeUpdate()

                prepareStatement = conn.prepareStatement("UPDATE account1 SET amount = amount + ? WHERE id = ?")
                prepareStatement.setLong(1, amount)
                prepareStatement.setLong(2, accountId2)

                prepareStatement.executeUpdate()
            } catch (exception: SQLException) {
                println(exception.message)
            }
        }
    }
}
