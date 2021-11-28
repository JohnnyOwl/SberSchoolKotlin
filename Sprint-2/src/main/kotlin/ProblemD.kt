class ProblemD {

    fun main() {

        val t = readInt()
        for (i in 0 until t) {
            val n = readLine()
            val a = readListLong()
            val result = a.maxOf { element -> element * a.count { current -> current >= element } }
            println(result)
        }
    }
}