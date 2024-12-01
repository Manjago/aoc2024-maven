import day01.day01Part1
import day01.day01Part2

fun main() {
    fun part1(input: List<String>): Int {
        return day01Part1(input)
    }

    fun part2(input: List<String>): Int {
        return day01Part2(input)
    }

    val testInput = readInput("Day01_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 11) { "Expected 11 but got $testPart1" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 31) { "Expected 31 but got $testPart2" }
    part2(input).println()
}
