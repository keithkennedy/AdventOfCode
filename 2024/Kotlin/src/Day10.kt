class Matrix(val grid: List<List<Int>>) {
    fun getValue(point: Point): Int? {
        return try {
            grid[point.row][point.column]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun surroundingPoints(originPoint: Point): List<Point> {
        var points = ArrayList<Point>(4)
        val lastRowIndex = this.grid.count() - 1
        val lastColumnIndex = this.grid[0].count() - 1

        val isPreviousRowValid = originPoint.row > 0
        val isForwardRowValid = originPoint.row < lastRowIndex
        val isPreviousColumnValid = originPoint.column > 0
        val isForwardColumnValid = originPoint.column < lastColumnIndex

        if (isPreviousRowValid) points.add(Point(originPoint.row - 1, originPoint.column))
        if (isForwardRowValid) points.add(Point(originPoint.row + 1, originPoint.column))
        if (isPreviousColumnValid) points.add(Point(originPoint.row, originPoint.column - 1))
        if (isForwardColumnValid) points.add(Point(originPoint.row, originPoint.column + 1))

        return points
    }
}

class Point(val row: Int, val column: Int)

fun main() {
    val input = readInput("Day10")
    val matrix = Matrix(input.map { line -> line.map { it.toString().toInt() } })

    println(matrix.grid.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { col, value -> endPoints(matrix, Point(rowIndex, col))
            // comment out below line for part 2!
            .distinctBy { it.row to it.column }
            .count() }
        }.sum()
    )
}

// given a start point -> what are the end points
fun endPoints(matrix: Matrix, start: Point): List<Point> {
    var endPoints = ArrayList<Point>()
    val currentNumber = matrix.getValue(start)
    if (currentNumber == null || currentNumber != 0) return endPoints
    endPoints.addAll(nextPoints(matrix, start, currentNumber))

    return endPoints
}

fun nextPoints(matrix: Matrix, point: Point, currentNumber: Int): List<Point> {
    val thisPointValue = matrix.getValue(point)
    if (thisPointValue == 9) {
        return listOf(point)
    }

    var n = ArrayList<Point>()
    val surroundingPoints = matrix.surroundingPoints(point)
    surroundingPoints.forEach { point ->
        val pointValue = matrix.getValue(point)
        if (pointValue != null && pointValue == currentNumber + 1) {
            n.addAll(nextPoints(matrix, point, currentNumber + 1))
        }
    }

    return n
}
