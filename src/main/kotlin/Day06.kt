import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST

fun main() {

    class Maze(val width: Int, val height: Int) {
        private val obstacles = mutableSetOf<Point>()

        fun addObstacle(point: Point) {
            obstacles.add(point)
        }

        fun hasObstacle(point: Point): Boolean {
            return obstacles.contains(point)
        }

        fun inMe(point: Point): Boolean {
            return point.x in 0..width-1 && point.y in 0..height-1
        }

        override fun toString(): String {
            return "Maze(width=$width, height=$height, obstacles=$obstacles)"
        }

    }

    fun parseMaze(input: List<String>): Pair<Maze, Point> {
        val maze = Maze(input[0].length, input.size)
        var pos: Point? = null
        for(y in 0 until input.size)
            for(x in 0 until input[0].length) {
                val char = input[y][x]
                when(char) {
                    '#' -> maze.addObstacle(Point(x, y))
                    '^' -> pos = Point(x, y)
                }
            }
        return maze to (pos?: throw IllegalStateException("No initial position"))
    }

    fun next(direction: Direction): Direction = when(direction) {
        NORTH -> EAST
        WEST -> NORTH
        EAST -> SOUTH
        SOUTH -> WEST
    }

    fun part1(input: List<String>): Int {
        val (maze, initialPos) = parseMaze(input)
        var direction = NORTH
        var pos = initialPos

        val seen = mutableSetOf<Point>()

        while (maze.inMe(pos)) {
             seen.add(pos)
             val nextStep = pos + direction
             if (maze.hasObstacle(nextStep)) {
                 direction = next(direction)
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
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
}
