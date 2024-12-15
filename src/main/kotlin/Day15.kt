fun main() {

    val BORDER = '#'
    val ROBOT = '@'
    val BOX = 'O'
    val SPACE = '.'

    class Maze(val data: Array<Array<Char>>, var robot: SimplePoint, val instructions: List<Char>) {
        val width = data[0].size
        val height = data.size

        val directions = mapOf<Char, SimplePoint>(
            '^' to SimplePoint(0, -1), 'v' to SimplePoint(0, 1), '>' to SimplePoint(1, 0), '<' to SimplePoint(-1, 0)
        )

        fun display(): String = buildString {
            for (y in 0 until height) {
                appendLine(data[y].joinToString(""))
            }
        }

        fun moveRobot(instructionNum: Int) {
            val result = move(robot, instructionNum)
            if (result) {
                robot = robot.newByDirections(instructionNum)
            }
        }

        private fun SimplePoint.get() = data[this.y][this.x]

        fun SimplePoint.newByDirections(i: Int) = this + this@Maze.directions[this@Maze.instructions[i]]!!

        private fun move(from: SimplePoint, instructionNum: Int): Boolean {
            val toPoint = from.newByDirections(instructionNum)
            return when {
                !toPoint.inBound(width, height) || toPoint.get() == BORDER -> false
                toPoint.get() == SPACE -> {
                    swap(toPoint, from)
                    true
                }

                else -> {
                    val result = move(toPoint, instructionNum)
                    if (result) {
                        swap(toPoint, from)
                    }
                    result
                }
            }
        }

        private fun swap(a: SimplePoint, b: SimplePoint) {
            val oldValue = data[a.y][a.x]
            data[a.y][a.x] = data[b.y][b.x]
            data[b.y][b.x] = oldValue
        }

        fun allBoxes(): List<SimplePoint> = data.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, char ->
                if (char == BOX) SimplePoint(x, y) else null
            }
        }
    }

    fun parseInput(lines: List<String>): Maze {
        val width = lines[0].length
        val height = lines.count { it.contains(BORDER) }
        val result = Array(height) { Array(width) { SPACE } }

        var robot: SimplePoint? = null
        for (y in 0 until height) {
            for (x in 0 until width) {
                val char = lines[y][x]
                result[y][x] = char
                if (char == ROBOT) {
                    robot = SimplePoint(x, y)
                }
            }
        }

        val instructions = mutableListOf<Char>()

        for (i in height + 1 until lines.size) {
            for (p in lines[i]) {
                instructions.add(p)
            }
        }

        return Maze(result, robot!!, instructions)
    }

    fun part1(input: List<String>): Int {
        val maze = parseInput(input)
        //println("Initial state:")
        //maze.display().println()
        for (i in 0 until maze.instructions.size) {
            maze.moveRobot(i)
            //println("Move ${maze.instructions[i]}:")
            //maze.display().println()
        }

        return maze.allBoxes().asSequence().map { it.y * 100 + it.x }.sum()
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day15_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 2028) { "Expected 2028 but got $testPart1" }

    val testInput2 = readInput("Day15_test_2")
    val testPart12 = part1(testInput2)
    check(testPart12 == 10092) { "Expected 10092 but got $testPart12" }

    val input = readInput("Day15")
    val part1 = part1(input)
    part1.println()
    check(part1 == 1414416) { "Expected 1414416 but got $part1" }/*
        val testPart2 = part2(testInput)
        check(testPart2 == 1) { "Expected 1 but got $testPart2" }
        part2(input).println()
    */
}
