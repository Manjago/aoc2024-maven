fun main() {

    val zero = LongPoint(0L, 0L)

    data class ClawItem(val buttonA: LongPoint, val buttonB: LongPoint, val prize: LongPoint)

    fun parseInput(input: List<String>): List<ClawItem> {
        var i = 0
        val result = mutableListOf<ClawItem>()
        while (i < input.size) {
            val a = input[i++].substringAfter("Button A: ").split(", ")
            val buttonA = LongPoint(a[0].substringAfter("X").toLong(), a[1].substringAfter("Y").toLong())
            val b = input[i++].substringAfter("Button B: ").split(", ")
            val buttonB = LongPoint(b[0].substringAfter("X").toLong(), b[1].substringAfter("Y").toLong())
            val p = input[i++].substringAfter("Prize: ").split(", ")
            val prize = LongPoint(p[0].substringAfter("X=").toLong(), p[1].substringAfter("Y=").toLong())
            result.add(ClawItem(buttonA, buttonB, prize))
            i++
        }
        return result.toList()
    }

    fun determinant(a: LongPoint, b: LongPoint): Long {
        return a.x * b.y - a.y * b.x
    }

    fun LongPoint.mod(d: Long): LongPoint = LongPoint(this.x % d, this.y % d)
    fun LongPoint.div(d: Long): LongPoint = LongPoint(this.x / d, this.y / d)

    fun ClawItem.tokens(prizeDist: Long): Long {
        val px = prize.x + prizeDist
        val py = prize.y + prizeDist
        val mult = LongPoint(buttonB.y * px - buttonB.x * py, -buttonA.y * px + buttonA.x * py)
        val d = determinant(buttonA, buttonB)
        return if (mult.mod(d) == zero) {
            val ab = mult.div(d)
            3 * ab.x + 1 * ab.y
        } else {
            0L
        }
    }

    fun part1(input: List<String>): Long {
        return parseInput(input).asSequence().map { it.tokens(0L) }.sum()
    }

    fun part2(input: List<String>): Long {
        return parseInput(input).asSequence().map { it.tokens(10_000_000_000_000L) }.sum()
    }

    val testInput = readInput("Day13_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 480L) { "Expected 480 but got $testPart1" }

    val input = readInput("Day13")
    val part1 = part1(input)
    part1.println()
    check(part1 == 31065L) { "Expected 31065 but got $part1" }

    val part2 = part2(input)
    part2.println()
    check(part2 == 93866170395343L) { "Expected 93866170395343 but got $part2" }
}
