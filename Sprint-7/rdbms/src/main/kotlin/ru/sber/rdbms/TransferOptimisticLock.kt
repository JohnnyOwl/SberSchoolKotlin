package ru.sber.rdbms

import java.lang.Exception
import java.sql.DriverManager

class TransferOptimisticLock {
    private val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/db",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        var oldVersion: Long
        val autoCommit = connection.autoCommit

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
                    oldVersion = resultSet.getLong("version")
                }

                preparedStatement =
                    conn.prepareStatement("UPDATE account1 SET amount = amount - ?, version = version + 1 WHERE id = ? and version = ?")
                preparedStatement.setLong(1, amount)
                preparedStatement.setLong(2, accountId1)
                preparedStatement.setLong(3, oldVersion)
                var result = preparedStatement.executeUpdate()
                if (result == 0) {
                    throw TransferException("Data Integrity Error")
                }

                preparedStatement =
                    conn.prepareStatement("SELECT * FROM account1 WHERE id = ?")
                preparedStatement.setLong(1, accountId2)
                preparedStatement.executeQuery().use { resultSet ->
                    resultSet.next()
                    oldVersion = resultSet.getLong("version")
                }

                preparedStatement =
                    conn.prepareStatement("UPDATE account1 SET amount = amount + ?, version = version + 1 WHERE id = ? and version = ?")
                preparedStatement.setLong(1, amount)
                preparedStatement.setLong(2, accountId2)
                preparedStatement.setLong(3, oldVersion)
                result = preparedStatement.executeUpdate()
                if (result == 0) {
                    throw TransferException("Data Integrity Error")
                }
                conn.commit()
            } catch (exception: Exception){
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
