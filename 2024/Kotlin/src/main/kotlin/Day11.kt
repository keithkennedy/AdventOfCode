fun main() {
    val s = Day11KtSolution()
//    val stones = readInput("Day11").first().splitToSequence(" ").map { l -> l.toLong() }.toList()
    val stones = listOf(0L)
    println(s.blinks(25, stones))
}

class Day11KtSolution {
    // returns number of stones after blinks
    fun blinks(totalBlinks: Int, stones: List<Long>): Int {
        var blinks = 0
        var s = stones
        while (blinks < totalBlinks) {
            s = s.flatMap { blink(it) }
            println(s)
            blinks++
        }
        return s.count()
    }

    val blinkMap = HashMap<Long, List<Long>>()

    fun blink(i: Long): List<Long> {
        if (blinkMap.containsKey(i)) {
            return blinkMap[i]!!
        }

        val result: List<Long>
        if (i == 0.toLong()) {
            result = listOf(1)
            blinkMap.put(i, result)
            return result
        }

        val stringNumber = i.toString()
        if (stringNumber.length % 2L == 0L) {
            val h = stringNumber.length / 2
            val s1 = stringNumber.slice(0 until h)
            val s2 = stringNumber.slice(h until stringNumber.length)

            result = listOf(s1.toLong(), s2.toLong())
            blinkMap.put(i, result)
            return result
        }

        result = listOf(i * 2024)
        blinkMap.put(i, result)
        return result
    }
}