import Direction.NORTH

fun main() {

    class Maze(val width: Int, val height: Int) {
        private val obstacles = mutableSetOf<SimplePoint>()

        fun addObstacle(point: SimplePoint) {
            obstacles.add(point)
        }

        fun hasObstacle(point: OrientedPoint): Boolean {
            return obstacles.contains(point.toSimplePoint())
        }

        fun inMe(point: OrientedPoint): Boolean {
            return point.x in 0..width - 1 && point.y in 0..height - 1
        }

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

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day06_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 41) { "Expected 41 but got $testPart1" }

    val input = readInput("Day06")
    val part1 = part1(input)
    part1.println()
    check(part1 == 5095) { "Expected 5095 but got $part1" }
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
}
