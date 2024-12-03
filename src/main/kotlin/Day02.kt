import kotlin.math.abs

fun main() {

    fun List<Int>.isIncrease(i: Int): Boolean = this[i] > this[i - 1]

    fun List<Int>.isNear(i: Int): Boolean {
        val dist = abs(this[i] - this[i - 1])
        return 1 <= dist && dist <= 3
    }

    fun List<Int>.isSafe(): Boolean {
        val isIncrease = isIncrease(1)
        for (i in 1..size - 1) {
            if (!isNear(i)) {
                return false
            }
            if (isIncrease(i).xor(isIncrease)) {
                return false
            }
        }
        return true
    }

    fun List<Int>.isSuperSafe(): Boolean {
        if (isSafe()) {
            return true
        }
        for (i in indices) {
            val modifiedList = withIndex().asSequence().filter { indexedValue -> indexedValue.index != i }
                .map { indexedValue -> indexedValue.value }.toList()
            if (modifiedList.isSafe()) {
                return true
            }
        }
        return false
    }

    fun List<String>.parseData(): List<List<Int>> = asSequence().map {
        it.split(" ").asSequence().map { it.toInt() }.toList()
    }.toList()

    fun part1(input: List<String>): Int {

        val data: List<List<Int>> = input.parseData()

        val safeReports = data.asSequence().map {
            val safe = it.isSafe()
            //println("$it is $safe")
            safe
        }.count { it }

        return safeReports
    }

    fun part2(input: List<String>): Int {
        val data: List<List<Int>> = input.parseData()

        val safeReports = data.asSequence().map {
            val safe = it.isSuperSafe()
            //println("$it is $safe")
            safe
        }.count { it }

        return safeReports
    }

    val testInput = readInput("Day02_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 2) { "Expected 2 but got $testPart1" }

    val input = readInput("Day02")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 4) { "Expected 4 but got $testPart2" }
    part2(input).println()
}
