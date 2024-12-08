import kotlin.math.abs

fun main() {
    fun count(list: List<Int>, func: (Int, Int) -> Boolean): Int {
        var count = 0
        for (i in list.indices) {
            if (i == list.lastIndex) continue
            if (func(list[i], list[i + 1])) count++
        }
        return count
    }

    fun increasingCount(list : List<Int>) : Int {
        return count(list) { x, y -> x > y }
    }
    fun decreasingCount(list : List<Int>) : Int {
        return count(list) { x, y -> x < y }
    }

    fun isSafe(report: List<Int>, allowBadLevel: Boolean = true): Boolean {
        val isIncreasing = increasingCount(report) < decreasingCount(report)

        for (i in report.indices) {
            if (i == report.lastIndex) continue

            val curInt = report[i]
            val nextInt = report[i + 1]

            val diff = abs(curInt - nextInt)
            if ((diff > 3)
                || (isIncreasing && nextInt < curInt)
                || (!isIncreasing && nextInt > curInt)
                || (nextInt == curInt))
            {
                if (allowBadLevel) {
                    return (
                            isSafe(
                        report.filterIndexed { index, _ -> index != i },
                        false)
                            ||
                            isSafe(
                                report.filterIndexed { index, _ -> index != i + 1 },
                                false)
                            )
                }
                return false
            }
        }
        return true
    }

    val input = readInput("Day02")
    var safeReports = 0
    for (line in input) {
        if (line.isEmpty()) continue
        val report = line.split(" ").map { it.toInt() }

        if (isSafe(report)) {
            safeReports++
        }
    }
    println(safeReports)
}
