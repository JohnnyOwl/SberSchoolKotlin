class ProblemA {

    fun main() {

        val t = readInt()
        for (i in 0 until t) {
            val input = readIntArray(3)
            val a = input[0].toLong()
            val b = input[1].toLong()
            val k = input[2].toLong()
            println(k / 2 * (a - b) + k % 2 * a)
        }
    }
}