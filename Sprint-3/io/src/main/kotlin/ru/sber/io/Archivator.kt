package ru.sber.io

import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class Archivator {

    fun zipLogfile(fileName: String) {
        ZipOutputStream(FileOutputStream("io/logfile.zip")).use { zos ->
            FileInputStream(fileName).use { fis ->
                val entry = ZipEntry("logfile.log")
                zos.putNextEntry(entry)

                val buffer = ByteArray(fis.available())
                fis.read(buffer)
                zos.write(buffer)
            }
        }
    }

    fun unzipLogfile(zipName: String) {
        ZipInputStream(FileInputStream(zipName)).use { zis ->
            FileOutputStream("io/unzippedLogfile.log").use { fos ->
                while (zis.nextEntry != null) {
                    var c = zis.read()
                    while (c != -1) {
                        fos.write(c)
                        c = zis.read()
                    }
                }
            }
        }
    }
}
