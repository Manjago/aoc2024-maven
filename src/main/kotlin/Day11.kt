import java.math.BigInteger

fun main() {

    val magic = BigInteger.valueOf(2024L)

    fun parseInput(line: String): List<BigInteger> = line.split(" ").asSequence().map { it.toBigInteger() }.toList()

    fun processing(stones: List<BigInteger>): List<BigInteger> {
        val result = mutableListOf<BigInteger>()
        for (stone in stones) {
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

    fun part1(input: List<String>): Int {
        var current = parseInput(input[0])
        repeat(25) {
            current = processing(current)
        }
        return current.size
    }

    fun dfs(
        memo: Array<MutableMap<BigInteger, BigInteger>>,
        stone: BigInteger,
        blink: Int,
        targetBlink: Int
    ): BigInteger = when {
        blink == targetBlink -> BigInteger.ONE

        memo[blink].containsKey(stone) -> memo[blink][stone]!!

        else -> {

            val asString = stone.toString()
            when {
                stone == BigInteger.ZERO ->
                    dfs(memo, BigInteger.ONE, blink + 1, targetBlink).also { memo[blink].put(stone, it) }

                asString.length % 2 == 0 -> {
                    val bound = asString.length / 2
                    val left = dfs(memo, BigInteger(asString.slice(0..bound - 1)), blink + 1, targetBlink)
                    val right =
                        dfs(memo, BigInteger(asString.slice(bound..asString.length - 1)), blink + 1, targetBlink)
                    (left + right).also { memo[blink].put(stone, it) }
                }

                else -> dfs(memo, stone * magic, blink + 1, targetBlink).also { memo[blink].put(stone, it) }
            }

        }
    }


    fun part2(input: List<String>, depth: Int): BigInteger {
        val initial = parseInput(input[0])

        val memo = Array<MutableMap<BigInteger, BigInteger>>(depth + 1) { mutableMapOf<BigInteger, BigInteger>() }

        var answer = BigInteger.ZERO
        for (stone in initial) {
            answer = answer + dfs(memo, stone, 0, depth)
        }

        return answer
    }

    val testInput = readInput("Day11_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 55312) { "Expected 55312 but got $testPart1" }

    val input = readInput("Day11")
    part1(input).println()
    val testPart2 = part2(testInput, 25)

    check(testPart2 == BigInteger.valueOf(55312L)) { "Expected 55312L but got $testPart2" }
    val part2 = part2(input, 75)
    part2.println()
    check(part2 == BigInteger.valueOf(235571309320764L)) { "Expected 235571309320764 but got $part2" }
}
