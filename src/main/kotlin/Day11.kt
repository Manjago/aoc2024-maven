import java.math.BigInteger

fun main() {

    val magic = BigInteger.valueOf(2024L)

    fun parseInput(line: String): List<BigInteger> = line.split(" ").asSequence().map { it.toBigInteger() }.toList()

    fun processing(stones: List<BigInteger>): List<BigInteger> {
        val result = mutableListOf<BigInteger>()
        for(stone in stones) {
            val asString = stone.toString()
            when {
                stone == BigInteger.ZERO -> result.add(BigInteger.ONE)
                asString.length % 2 == 0 -> {
                    val bound = asString.length / 2
                    result.add(BigInteger(asString.slice(0..bound - 1)))
                    result.add(BigInteger(asString.slice(bound..asString.length - 1)))
                }
                else -> result.add(stone * magic)
            }
        }
        return result.toList()
    }

    fun tost(input: List<String>, repCount: Int, short: Boolean) {
        var current = parseInput(input[0])
        var counter = 0
        if (short) {
            println("$counter ${current.size}")
        } else {
            println("$counter $current")
        }
        repeat(repCount) {
            val start = System.currentTimeMillis()
            current = processing(current)
            val finish = System.currentTimeMillis() - start
            counter++
            if (short) {
                println("$counter ${current.size} in ${finish} ms")
            } else {
                println("$counter $current in ${finish} ms")
            }
        }
    }

    fun part1(input: List<String>): Int {
        var current = parseInput(input[0])
        //println(current)
        repeat(25) {
            current = processing(current)
            //println(current)
        }
        return current.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val tostInput = readInput("Day11_tost")
    tost(tostInput, 45, true)

    /*
    val testInput = readInput("Day10_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 55312) { "Expected 55312 but got $testPart1" }

    val input = readInput("Day11")
    part1(input).println()
        val testPart2 = part2(testInput)
        check(testPart2 == 1) { "Expected 1 but got $testPart2" }
        part2(input).println()
    */
}
