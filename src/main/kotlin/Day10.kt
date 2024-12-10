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

    fun Array<IntArray>.display(): String {
        val result = StringBuilder()
        for(y in this.indices) {
            for(x in this[0].indices) {
                result.append(this[y][x])
            }
            result.append('\n')
        }
        return result.toString()
    }


    fun inBound(x: Int, y: Int, width: Int, height: Int): Boolean = x in 0..width - 1 && y in 0..height - 1

    fun Array<IntArray>.next(point: IntValuePoint, width: Int, height: Int): Sequence<IntValuePoint> {
       val result = mutableListOf<IntValuePoint>()
       var x = point.x+1
       var y = point.y
       if (inBound(x, y, width, height) && this[y][x] == point.value + 1) {
           result.add(IntValuePoint(x, y, this[y][x]))
       }

       x = point.x-1
       y = point.y
       if (inBound(x, y, width, height) && this[y][x] == point.value + 1) {
           result.add(IntValuePoint(x, y, this[y][x]))
       }

       x = point.x
       y = point.y + 1
       if (inBound(x, y, width, height) && this[y][x] == point.value + 1) {
           result.add(IntValuePoint(x, y, this[y][x]))
       }

       x = point.x
       y = point.y - 1
       if (inBound(x, y, width, height) && this[y][x] == point.value + 1) {
           result.add(IntValuePoint(x, y, this[y][x]))
       }
       return result.asSequence()
    }

    fun List<IntValuePoint>.finger(): String {
        val sb = StringBuilder()
        this.forEach {
            sb.append(":"+ it.x + ","+ it.y)
        }
        return sb.toString()
    }

    fun part1(input: List<String>): Int {
        val maze = parseInput(input)
        val width = maze[0].size
        val height = maze.size
        //debug(maze.display())

        val queue = ArrayDeque<List<IntValuePoint>>()

        for(y in maze.indices) {
            for(x in maze[0].indices) {
                val item = maze[y][x]
                if (item == 0) {
                    queue.add(listOf(IntValuePoint(x, y, item)))
                }
            }
        }

        val paths = HashSet<String>()
        val good = mutableSetOf<IntValuePoint>()
        while(queue.isNotEmpty()) {
            val path: List<IntValuePoint> = queue.removeFirst()
            //debug("Got path: $path")
            val point = path.last()

            if (point.value == 9) {
                paths.add(path.finger())
                good.add(point)
            } else {
                maze.next(point, width, height).forEach {
                    val newItem: MutableList<IntValuePoint> = ArrayList(path)
                    newItem.add(it)
                    queue += newItem
                }
            }
        }
        //debug(paths.size.toString())
        //debug(good.size.toString())
        return good.size
    }

    fun part2(input: List<String>): Int {
        return 1
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

/*
    val input = readInput("Day10")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
*/
}
