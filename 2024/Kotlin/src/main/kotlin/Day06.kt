enum class GuardDirection {
    LEFT, RIGHT, FORWARD, BACKWARD;
}

fun main() {
    class Space(val x: Int, val y: Int) {
        override fun toString(): String {
            return "."
        }
    }

    class PreviouslyOccupiedSpace(val x: Int, val y: Int, val guardDirection: GuardDirection) {
        override fun toString(): String {
            return "X"
        }
    }

    class Obstacle(var x: Int, var y: Int) {
        override fun toString(): String {
            return "#"
        }
    }

    class Guard(var x: Int, var y: Int, var direction: GuardDirection) {
        override fun toString(): String {
            return when (direction) {
                GuardDirection.FORWARD -> "^"
                GuardDirection.BACKWARD -> "v"
                GuardDirection.LEFT -> "<"
                GuardDirection.RIGHT -> ">"
            }
        }

        fun nextPosition() : Pair<Int, Int> {
            return when (direction) {
                GuardDirection.FORWARD -> Pair(x - 1, y)
                GuardDirection.BACKWARD -> Pair(x + 1, y)
                GuardDirection.LEFT -> Pair(x, y - 1)
                GuardDirection.RIGHT -> Pair(x, y + 1)
            }
        }
        fun rotateRight() {
            direction = when (direction) {
                GuardDirection.FORWARD -> GuardDirection.RIGHT
                GuardDirection.BACKWARD -> GuardDirection.LEFT
                GuardDirection.LEFT -> GuardDirection.FORWARD
                GuardDirection.RIGHT -> GuardDirection.BACKWARD
            }
        }
    }

    class Map {
        val spaces: ArrayList<Space> = ArrayList<Space>()
        val obstacles: ArrayList<Obstacle> = ArrayList<Obstacle>()
        val guards: ArrayList<Guard> = ArrayList<Guard>()

        var originalGuardX: Int = 0
        var originalGuardY: Int = 0
        val spacesGuardVisited: MutableSet<PreviouslyOccupiedSpace> = mutableSetOf()

        var lastRowIndex: Int = 0
        var lastColumnIndex: Int = 0

        var isGuardInMap : Boolean = true
            get() = field
            set(value) {
                field = value
            }
        var isLooped : Boolean = false
            get() = field
            set(value) {
                field = value
            }

        constructor(input: List<String>) {
            lastRowIndex = input.lastIndex
            lastColumnIndex = input[0].lastIndex

            input.forEachIndexed { rowIdx, row ->
                for ((columnIdx, char) in row.withIndex()) {
                    if (char == '#') {
                        obstacles.add(Obstacle(rowIdx, columnIdx))
                        continue
                    }
                    if (char == '^') {
                        guards.add(Guard(rowIdx, columnIdx, GuardDirection.FORWARD))
                        originalGuardX = rowIdx
                        originalGuardY = columnIdx
                        // also add the space the guard is covering
                    }

                    spaces.add(Space(rowIdx, columnIdx))
                }
            }
        }

        fun reset() {
            isLooped = false
            isGuardInMap = true
            val guard = guards.first()
            guard.direction = GuardDirection.FORWARD
            guard.x = originalGuardX
            guard.y = originalGuardY
            spacesGuardVisited.clear()
        }

        fun tick() {
            val guard = guards.first()
            val curOccupiedSpace = spaces.first { it.x == guard.x && it.y == guard.y }
            val previouslyOccupiedSpace = PreviouslyOccupiedSpace(curOccupiedSpace.x,curOccupiedSpace.y, guard.direction)

            if (spacesGuardVisited.any {
                it.x == previouslyOccupiedSpace.x
                        && it.y == previouslyOccupiedSpace.y
                        && it.guardDirection == previouslyOccupiedSpace.guardDirection
            }) {
                isLooped = true
                return
            }
            spacesGuardVisited.add(previouslyOccupiedSpace)

            val nextPosition = guard.nextPosition()
            val nextSpace = spaces.firstOrNull { it.x == nextPosition.first && it.y == nextPosition.second }
            val nextObstacle = obstacles.firstOrNull { it.x == nextPosition.first && it.y == nextPosition.second }
            if (nextObstacle != null) {
                guard.rotateRight()
            } else if (nextSpace != null) {
                // we have a space guard can move to
                guard.x = nextSpace.x
                guard.y = nextSpace.y
            } else {
                isGuardInMap = false
            }
        }

        fun output() {
            for (row in 0 until lastRowIndex + 1) {
                for (column in 0 until lastColumnIndex + 1) {
                    val guard = guards.firstOrNull { it.x == row && it.y == column }
                    if (guard != null) {
                        print(guard.toString())
                        continue
                    }
                    val obstacle = obstacles.firstOrNull { it.x == row && it.y == column }
                    if (obstacle != null) {
                        print(obstacle.toString())
                        continue
                    }
                    print(".")
                }
                print("\n")
            }
        }
    }

//    val input = .readInput("Day06_example")
    val input = readInput("Day06")
    val map = Map(input)

    // part 1
    while (map.isGuardInMap && !map.isLooped) {
        map.tick()
    }
    println(map.spacesGuardVisited.distinctBy { it.x to it.y }.count())

    // part 2
    var newObstacle = Obstacle(0, 0)
    map.obstacles.add(newObstacle)
    var loops = 0
    for (row in 0 until map.lastRowIndex + 1) {
        for (column in 0 until map.lastColumnIndex + 1) {
            val guard = map.guards.first()

            map.reset()


            if (!(guard.x == row && guard.y == column)) {
                newObstacle.x = row
                newObstacle.y = column
            } else {
                continue
            }

            // run map.tick() and check if looped at end - or not
            // keep count of loops
            while (map.isGuardInMap && !map.isLooped) {
                map.tick()
            }
            if (map.isLooped) {
                println("${row}-${column}")
                loops++
            }
        }
    }
    println(loops)
}