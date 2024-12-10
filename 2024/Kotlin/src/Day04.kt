enum class Direction {
    LEFT, RIGHT, UP, DOWN, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    fun nextPath(row: Int, column: Int): Pair<Int, Int> {
        return when (this) {
            LEFT -> Pair(row, column + 1)
            RIGHT -> Pair(row, column - 1)
            UP -> Pair(row - 1, column)
            DOWN -> Pair(row + 1, column)
            TOP_LEFT -> Pair(row - 1, column - 1)
            TOP_RIGHT -> Pair(row + 1, column - 1)
            BOTTOM_LEFT -> Pair(row - 1, column + 1)
            BOTTOM_RIGHT -> Pair(row + 1, column + 1)
        }
    }

    fun oppositeDirection(): Direction{
        return when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
            TOP_LEFT -> BOTTOM_RIGHT
            TOP_RIGHT -> BOTTOM_LEFT
            BOTTOM_LEFT -> TOP_RIGHT
            BOTTOM_RIGHT -> TOP_LEFT
        }
    }
}

fun main() {
//    val input = readInput("Day04_example")
    val input = readInput("Day04")
//    part1(input)
    // TODO: remove all non "xmas" letters from this
    part2(input)
}

fun part1(input: List<String>) {
    val matrix = input.map { it.toList() }
    val word = "XMAS"
    println(occurrences(word, matrix))
}

fun part2(input: List<String>) {
    // MAS crosses
    val matrix = input.map { it.toList() }
    println(masOccurrences(matrix))
}

fun masOccurrences(matrix: List<List<Char>>): Int {
    var occurrences = 0
    matrix.forEachIndexed { rowIdx, row ->
        row.forEachIndexed { columnIdx, _ ->
            if (isMasCross(matrix, rowIdx, columnIdx)) occurrences++
        }
    }
    return occurrences
}

fun isMasCross(matrix: List<List<Char>>, rowIdx: Int, columnIdx: Int): Boolean {
    val letter = matrix[rowIdx][columnIdx]
    if (letter == 'A') {
        // do we have a "M" around the letter?
            // if so; then do we have a "S" opposite?
                // add to count
            // is count >= 2 -> return true
        // return false

        var crossMatches = 0
        crossMatches += Direction.entries.map { direction ->
            if (listOf(
                    // Exclude these, as we will check these as opposites of the rest
                    Direction.LEFT,
                    Direction.DOWN,
                    Direction.UP,
                    Direction.TOP_RIGHT,
                    Direction.RIGHT,
                    Direction.BOTTOM_RIGHT
            ).contains(direction)) {
                0
            } else {
                val letterCheckLocation = direction.nextPath(rowIdx, columnIdx)
                val oppositeLetterCheckLocation = direction.oppositeDirection().nextPath(rowIdx, columnIdx)
                try {
                    val letters = listOf(
                        letter(matrix, letterCheckLocation.first, letterCheckLocation.second),
                        letter(matrix, oppositeLetterCheckLocation.first, oppositeLetterCheckLocation.second)
                    )
                    if (letters.contains('M') && letters.contains('S')) 1 else 0
                } catch (IndexOutOfBoundsException: IndexOutOfBoundsException) {
                    0
                }
            }
        }.sum()

        return crossMatches >= 2
    }
    return false
}

fun letter(matrix: List<List<Char>>, rowIdx: Int, columnIdx: Int): Char? {
    try {
        return matrix[rowIdx][columnIdx]
    } catch (IndexOutOfBoundsException: IndexOutOfBoundsException) {
        return null
    }
}

fun occurrences(word: String, matrix: List<List<Char>>): Int {
    var occurrences = 0
    matrix.forEachIndexed { rowIdx, row ->
        row.forEachIndexed { columnIdx, _ ->
            occurrences += wordsOriginating(word, matrix, rowIdx, columnIdx)
        }
    }
    return occurrences
}

// Takes a co-ord and then tells you how many full words come from this point
fun wordsOriginating(word: String, matrix: List<List<Char>>, row: Int, column: Int): Int {
    var wordsOriginating = 0
    val currentLetter = matrix[row][column]
    val firstLetter = word[0]

    if (firstLetter == currentLetter) {
        wordsOriginating += Direction.entries.map { if (isFullWord(word, word, matrix, row, column, it)) 1 else 0 }.sum()
    }
    return wordsOriginating
}

fun isFullWord(word: String, originWord: String, matrix: List<List<Char>>, row: Int, column: Int, direction: Direction, index: Int = 0): Boolean {
    try {
        val thisLetter = matrix[row][column]
        if (thisLetter == originWord[index]) {
            if (word.length == 1) return true // we've got to end successfully
            val nextPath = direction.nextPath(row, column)
            return isFullWord(originWord.substring(index + 1, originWord.length), originWord, matrix, nextPath.first, nextPath.second, direction, index + 1)
        }
    } catch (indexOutOfBoundsException: IndexOutOfBoundsException) {
        return false
    }
    return false
}
