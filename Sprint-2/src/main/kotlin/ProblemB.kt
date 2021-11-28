class ProblemB {

    fun main() {

        val t = readInt()
        for (i in 0 until t) {
            val (n, k) = readListInt()
            for (j in 0 until n) {
                print('a' + j % k)
            }
            println()
        }
    }
}