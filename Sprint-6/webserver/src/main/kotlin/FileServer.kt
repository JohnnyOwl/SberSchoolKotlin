import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket


class FileServer {
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {
        socket.use { serverSocket ->
            while (true) {
                serverSocket.accept().use { socket ->
                    socket.getInputStream().bufferedReader().use { reader ->
                        PrintWriter(socket.getOutputStream()).use { printWriter ->
                            printWriter.println(getResponse(reader.readLine(), fs))
                            printWriter.flush()
                        }
                    }
                }
            }
        }
    }

    private fun getResponse(clientRequest: String, fs: VFilesystem): String {
        if (clientRequest.subSequence(0, 3) == "GET") {
            val path = clientRequest.substringAfter(" ").substringBefore(" ")

            if (fs.readFile(VPath(path)) != null)
                return "HTTP/1.0 200 OK\r\n" +
                        "Server: FileServer\r\n" +
                        "\r\n" +
                        "${fs.readFile(VPath(path))}\r\n"
        }
        return "HTTP/1.0 404 Not Found\r\n\n" +
                "Server: FileServer\r\n\n" +
                "\r\n"
    }
}
