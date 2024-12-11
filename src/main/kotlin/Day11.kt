
fun main() {

    val magic = 2024L

    fun parseInput(line: String): List<Long> = line.split(" ").asSequence().map { it.toLong() }.toList()

    fun digitCount(n: Long): Int = if (n < 10) {
        1
    } else {
        var count = 0
        var temp = n
        while (temp > 0) {
            temp /= 10
            count++
        }
        count
    }

    fun splitNumber(n: Long, count: Int): Pair<Long, Long> {
        if (n < 10) {
            return Pair(n, 0)
        }

        val half = count / 2
        var mid = 1L
        repeat(half) {
            mid *= 10
        }

        val left = n / mid
        val right = n % mid

        return Pair(left, right)
    }

    fun dfs(
        memo: Array<MutableMap<Long, Long>>,
        stone: Long,
        blink: Int,
        targetBlink: Int
    ): Long = when {
        blink == targetBlink -> 1L

        memo[blink].containsKey(stone) -> memo[blink][stone]!!

        else -> {

            val digitCount = digitCount(stone)
            when {
                stone == 0L ->
                    dfs(memo, 1L, blink + 1, targetBlink).also { memo[blink].put(stone, it) }

                digitCount % 2 == 0 -> {
                    val (leftSplitted, rightSplitted) = splitNumber(stone, digitCount)
                    val left = dfs(memo, leftSplitted, blink + 1, targetBlink)
                    val right =
                        dfs(memo, rightSplitted, blink + 1, targetBlink)
                    (left + right).also { memo[blink].put(stone, it) }
                }

                else -> dfs(memo, stone * magic, blink + 1, targetBlink).also { memo[blink].put(stone, it) }
            }

        }
    }

    fun core(input: List<String>, depth: Int): Long {
        val initial = parseInput(input[0])

        val memo = Array<MutableMap<Long, Long>>(depth + 1) { mutableMapOf<Long, Long>() }

        var answer = 0L
        for (stone in initial) {
            answer = answer + dfs(memo, stone, 0, depth)
        }

        return answer
    }



    val testInput = readInput("Day11_test")
    val testPart1 = core(testInput, 25)
    check(testPart1 == 55312L) { "Expected 55312 but got $testPart1" }

    val input = readInput("Day11")
    val part1 = core(input, 25)
    part1.println()
    check(part1 == 198075L) { "Expected 198075 but got $part1" }

    val part2 = core(input, 75)
    part2.println()
    check(part2 == 235571309320764L) { "Expected 235571309320764 but got $part2" }
}
