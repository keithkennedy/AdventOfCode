import kotlin.contracts.contract

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

    class Obstacle(val x: Int, val y: Int) {
        override fun toString(): String {
            return "#"
        }
    }
    class Guard(var x: Int, var y: Int, var direction: GuardDirection) {
        override fun toString(): String {
            return when (direction) {
                GuardDirection.FORWARD -> "^"
                GuardDirection.BACKWARD -> "▼"
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

        val spacesGuardVisited: ArrayList<PreviouslyOccupiedSpace> = ArrayList<PreviouslyOccupiedSpace>()

        var lastRowIndex: Int = 0
        var lastColumnIndex: Int = 0

        var isGuardInMap : Boolean = true
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
                        // also add the space the guard is covering
                    }

                    spaces.add(Space(rowIdx, columnIdx))
                }
            }
        }

        /*
        part 2:
        - if the history keeps a track of direction
        - then we can detect a loop when a perfect repeat is triggered (i.e., x/y/direction match a previous value)
        (We're doing above now!)

        - we know it's not a loop when the guard exits the map
        - so we can iterate through each space location (except one where guard started)
            and check each location to see if a loop is triggered when we add
            an obstacle in that location
         */

        fun tick() {
            val guard = guards.first()
            val curOccupiedSpace = spaces.first { it.x == guard.x && it.y == guard.y }
            spacesGuardVisited.add(PreviouslyOccupiedSpace(curOccupiedSpace.x,curOccupiedSpace.y, guard.direction))

            val nextPosition = guard.nextPosition()
            val nextSpace = spaces.firstOrNull { it.x == nextPosition.first && it.y == nextPosition.second }
            val nextObstacle = obstacles.firstOrNull { it.x == nextPosition.first && it.y == nextPosition.second }
            if (nextSpace != null) {
                // we have a space guard can move to
                guard.x = nextSpace.x
                guard.y = nextSpace.y
            } else if (nextObstacle != null) {
                guard.rotateRight()
            } else {
                isGuardInMap = false
            }

            //println("---")
            //output()
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

//    val input = readInput("Day06_example")
    val input = readInput("Day06")
    val map = Map(input)
    while (map.isGuardInMap) {
        map.tick()
    }
    println(map.spacesGuardVisited.distinctBy { it.x to it.y }.count())
}