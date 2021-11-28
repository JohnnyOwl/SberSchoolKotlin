package ru.sber.nio

import java.io.File
import java.nio.file.Files
import kotlin.io.path.name
import kotlin.io.path.useLines

class Grep {

    fun find(subString: String) {
        val fileName = "io/result.txt"
        val file = File(fileName)
        val searchPath = File("io/logs").toPath()

        file.outputStream().use { stream ->
            Files.find(searchPath, 2, { p, _ -> p.toString().endsWith(".log") }).forEach { log ->
                log.useLines { lines ->
                    lines.filter { s ->
                        s.contains(subString)
                    }.forEach { line ->
                        stream.write(("${log.name} : ${log.useLines { it.indexOf(line) + 1 }} : $line\n").toByteArray())
                    }
                }
            }
        }
    }
}