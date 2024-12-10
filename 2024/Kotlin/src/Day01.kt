import kotlin.math.abs

fun main() {
    val input = readInput("Day01")
    val pairs = input.map { line -> line.split("   ")}.map { listOf(it[0].toInt(), it[1].toInt()) }
    // part 1
    val orderedLeft = pairs.map { it[0] }.sortedBy { it }
    val orderedRight = pairs.map { it[1] }.sortedBy { it }
    val partOneAnswer = List(orderedLeft.size) { index ->
        abs(orderedLeft[index] - orderedRight[index])
    }.sum()
    println(partOneAnswer)

    // part 2
    val partTwoAnswer = List(orderedLeft.size) { index ->
        val thisSide = orderedLeft[index]
        thisSide * orderedRight.count { thisSide == it }
    }.sum()
    println(partTwoAnswer)
}
