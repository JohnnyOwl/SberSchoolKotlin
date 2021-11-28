package ru.sber.rdbms

import java.sql.DriverManager

class TransferPessimisticLock {

    val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/db",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->

            try {
                conn.autoCommit = false
                var preparedStatement =
                    conn.prepareStatement("SELECT * FROM account1 WHERE id = ?")
                preparedStatement.setLong(1, accountId1)
                preparedStatement.executeQuery().use { resultSet ->
                    resultSet.next()
                    if (resultSet.getLong("amount") - amount < 0) {
                        throw TransferException("Not enough money")
                    }
                }

                preparedStatement =
                    conn.prepareStatement("SELECT * FROM account1 WHERE id IN (?,?) FOR UPDATE")
                preparedStatement.setLong(1, accountId1)
                preparedStatement.setLong(2, accountId2)
                preparedStatement.executeQuery()

                preparedStatement =
                    conn.prepareStatement("UPDATE account1 SET amount = amount - ? WHERE id = ?")
                preparedStatement.setLong(1, amount)
                preparedStatement.setLong(2, accountId1)
                preparedStatement.executeUpdate()

                preparedStatement =
                    conn.prepareStatement("UPDATE account1 SET amount = amount + ? WHERE id = ?")
                preparedStatement.setLong(1, amount)
                preparedStatement.setLong(2, accountId2)
                preparedStatement.executeUpdate()

                conn.commit()
            } catch (exception: Exception) {
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = true
            }
        }
    }
}

fun main(){
    TransferPessimisticLock().transfer(1,2,500)
}
