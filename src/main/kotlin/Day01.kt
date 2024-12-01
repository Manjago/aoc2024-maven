import day01.day01Part1

fun main() {
    fun part1(input: List<String>): Int {
        return day01Part1(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 11) { "Expected 11 but got $testPart1" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    /*
        part2(input).println()
    */
}
