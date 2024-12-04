fun main() {

    fun Array<String>.inBounds(x: Int, y: Int, width: Int, height: Int): Boolean = x in 0..width - 1 && y in 0..height - 1

    fun Array<String>.check(x: Int, y: Int, shiftX: Int, shiftY: Int) : Boolean {
        val width = this[0].length
        val height = this.size

        var nextX = x + shiftX
        var nextY = y + shiftY

        when {
            !inBounds(nextX, nextY, width, height) -> {
                return false
            }
            this[nextY][nextX] != 'M' -> return false
        }

        nextX += shiftX
        nextY += shiftY

        when {
            !inBounds(nextX, nextY, width, height) -> {
                return false
            }
            this[nextY][nextX] != 'A' -> return false
        }

        nextX += shiftX
        nextY += shiftY

        return when {
            !inBounds(nextX, nextY, width, height) -> {
                false
            }

            this[nextY][nextX] != 'S' -> false
            else -> true
        }
    }

    fun part1(input: List<String>): Int {
        val data: Array<String> = Array(input[0].length) { "" }
        for(y in input.size - 1 downTo 0) {
            data[y] = input[y]
        }

        val width = input[0].length
        val height = input.size
        var answer = 0

        for(y in 0 until height) {
            for (x in 0 until width) {
                val char = data[y][x]
                if (char != 'X') {
                    continue
                }

                if (data.check(x, y, 1, 0)) {
                    ++answer
                }
                if (data.check(x, y, 1, 1)) {
                    ++answer
                }
                if (data.check(x, y, 1, -1)) {
                    ++answer
                }
                if (data.check(x, y, -1, 0)) {
                    ++answer
                }
                if (data.check(x, y, -1, 1)) {
                    ++answer
                }
                if (data.check(x, y, -1, -1)) {
                    ++answer
                }
                if (data.check(x, y, 0, 1)) {
                    ++answer
                }
                if (data.check(x, y, 0, -1)) {
                    ++answer
                }

            }
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day04_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 18) { "Expected 1 but got $testPart1" }

    val input = readInput("Day04")
    part1(input).println()
/*
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
*/
}
