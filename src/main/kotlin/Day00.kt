fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day00_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 1) { "Expected 1 but got $testPart1" }

    val input = readInput("Day01")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
}
