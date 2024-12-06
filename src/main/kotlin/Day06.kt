import Direction.NORTH

fun main() {

    class Maze(
        val width: Int,
        val height: Int,
        private val obstacles: MutableSet<SimplePoint> = mutableSetOf<SimplePoint>()
    ) {

        fun addObstacle(point: SimplePoint) {
            obstacles.add(point)
        }

        fun hasObstacle(point: OrientedPoint): Boolean {
            return hasObstacle(point.toSimplePoint())
        }

        fun hasObstacle(point: SimplePoint): Boolean = obstacles.contains(point)

        fun isFree(point: SimplePoint): Boolean = !hasObstacle(point) && inMe(point)

        private fun inMe(x: Int, y: Int): Boolean = x in 0..width - 1 && y in 0..height - 1

        fun inMe(point: OrientedPoint): Boolean = inMe(point.x, point.y)

        fun inMe(point: SimplePoint): Boolean = inMe(point.x, point.y)

        fun withObstacle(point: SimplePoint): Maze =
            Maze(width, height, obstacles.toMutableSet()).apply { this.addObstacle(point) }

        override fun toString(): String {
            return "Maze(width=$width, height=$height, obstacles=$obstacles)"
        }

    }

    fun parseMaze(input: List<String>): Pair<Maze, OrientedPoint> {
        val maze = Maze(input[0].length, input.size)
        var pos: OrientedPoint? = null
        for (y in 0 until input.size) for (x in 0 until input[0].length) {
            val char = input[y][x]
            when (char) {
                '#' -> maze.addObstacle(SimplePoint(x, y))
                '^' -> pos = OrientedPoint(x, y, NORTH)
            }
        }
        return maze to (pos ?: throw IllegalStateException("No initial position"))
    }

    fun part1(input: List<String>): Int {
        val (maze, initialPos) = parseMaze(input)
        var pos = initialPos

        val seen = mutableSetOf<SimplePoint>()

        while (maze.inMe(pos)) {
            seen.add(SimplePoint(pos.x, pos.y))
            val nextStep = pos.step()
            if (maze.hasObstacle(nextStep)) {
                pos = pos.turnRight()
            } else {
                pos = nextStep
            }
        }

        return seen.size
    }

    fun hasLoop(maze: Maze, from: OrientedPoint): Boolean {
        var current = from
        val seen = mutableSetOf<OrientedPoint>()
        while(maze.inMe(current)) {
            if (seen.contains(current)) {
                return true
            }
            seen.add(current)

            val nextStep = current.step()
            if (maze.hasObstacle(nextStep)) {
                current = current.turnRight()
            } else {
                current = nextStep
            }

        }
        return false
    }

    fun part2(input: List<String>): Int {

        val (maze, pos) = parseMaze(input)

        var answer = 0
        for (y in 0 until maze.height) for (x in 0 until maze.width) {
            val currentPoint = SimplePoint(x, y)
            if (maze.isFree(currentPoint) && hasLoop(maze.withObstacle(currentPoint), pos)) {
                ++answer
            }
        }

        return answer
    }

    val testInput = readInput("Day06_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 41) { "Expected 41 but got $testPart1" }

    val input = readInput("Day06")
    val part1 = part1(input)
    part1.println()
    check(part1 == 5095) { "Expected 5095 but got $part1" }
    val testPart2 = part2(testInput)
    check(testPart2 == 6) { "Expected 6 but got $testPart2" }
    part2(input).println()
}
