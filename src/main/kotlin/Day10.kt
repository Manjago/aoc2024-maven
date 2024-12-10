fun main() {

    fun parseInput(input: List<String>): Array<IntArray> {
        val result = Array<IntArray>(input.size) { IntArray(input[it].length) }
        for(y in input.indices) {
            for(x in input[y].indices) {
                if (input[y][x] == '.') {
                    result[y][x] = 999
                } else {
                    result[y][x] = input[y][x].digitToInt()
                }
            }
        }
        return result
    }

    fun inBound(x: Int, y: Int, width: Int, height: Int): Boolean = x in 0..width - 1 && y in 0..height - 1

    fun Array<IntArray>.next(point: IntValuePoint, width: Int, height: Int): Sequence<IntValuePoint> {
        val result = mutableListOf<IntValuePoint>()
        val directions = arrayOf(
            Pair(1, 0),   // right
            Pair(-1, 0),  // left
            Pair(0, 1),   // down
            Pair(0, -1)   // up
        )

        for ((dx, dy) in directions) {
            val x = point.x + dx
            val y = point.y + dy
            if (inBound(x, y, width, height) && this[y][x] == point.value + 1) {
                result.add(IntValuePoint(x, y, this[y][x]))
            }
        }
        return result.asSequence()
    }
    
    fun core(input: List<String>): HashSet<List<IntValuePoint>> {
        val maze = parseInput(input)
        val width = maze[0].size
        val height = maze.size

        val queue = ArrayDeque<List<IntValuePoint>>()

        for(y in maze.indices) {
            for(x in maze[0].indices) {
                val item = maze[y][x]
                if (item == 0) {
                    queue.add(listOf(IntValuePoint(x, y, item)))
                }
            }
        }

        val paths = HashSet<List<IntValuePoint>>()
        while(queue.isNotEmpty()) {
            val path: List<IntValuePoint> = queue.removeFirst()
            val point = path.last()

            if (point.value == 9) {
                paths.add(path)
            } else {
                maze.next(point, width, height).forEach {
                    val newItem: MutableList<IntValuePoint> = ArrayList(path)
                    newItem.add(it)
                    queue += newItem
                }
            }
        }

        return paths
    }

    fun part1(input: List<String>): Int {
        val paths = core(input)
        val origins = paths.asSequence().map { it.first() }.toSet()
        val scores = origins.asSequence().map { origin ->
            paths.asSequence().filter { path -> path.first() == origin }
                .map { it.last() }.toSet().size
        }.sum()

        return scores
    }

    fun part2(input: List<String>): Int {
        val paths = core(input)
        return paths.size
    }

    val testInput1 = readInput("Day10_test1")
    val testPart1 = part1(testInput1)
    check(testPart1 == 1) { "Expected 1 but got $testPart1" }

    val testInput2 = readInput("Day10_test2")
    val testPart2 = part1(testInput2)
    check(testPart2 == 2) { "Expected 2 but got $testPart2" }

    val testInput3 = readInput("Day10_test3")
    val testPart3 = part1(testInput3)
    check(testPart3 == 4) { "Expected 4 but got $testPart3" }

    val testInput4 = readInput("Day10_test4")
    val testPart4 = part1(testInput4)
    check(testPart4 == 36) { "Expected 36 but got $testPart4" }

    val input = readInput("Day10")
    val part1 = part1(input)
    part1.println()
    check(part1 == 717) { "Expected 717 but got $testPart4" }

    val testPart2Part2 = part2(testInput4)
    check(testPart2Part2 == 81) { "Expected 81 but got $testPart2" }
    val part2 = part2(input)
    part2.println()
    check(part2 == 1686) { "Expected 1686 but got $testPart4" }
}
