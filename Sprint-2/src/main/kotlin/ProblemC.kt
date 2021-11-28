class ProblemC {

    fun main() {

        val n = readInt()
        val a = readListInt().sorted()
        var result = 0
        for (i in 0 until n step 2) {
            result += a[i + 1] - a[i]
        }
        println(result)
    }
}