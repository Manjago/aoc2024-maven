fun main() {

    data class Robot(val pos: SimplePoint, val velocity: SimplePoint)

    fun parseInput(input: List<String>): List<Robot> = input.map {
        val tokens = it.split(" ")
        val prePos = tokens[0].substringAfter("p=").split(",")
        val pos = SimplePoint(prePos[0].toInt(), prePos[1].toInt())
        val preVelo = tokens[1].substringAfter("v=").split(",")
        val velocity = SimplePoint(preVelo[0].toInt(), preVelo[1].toInt())
        Robot(pos, velocity)
    }.toList()

    fun List<Robot>.toField(height: Int, width: Int): Array<Array<Int>> {
        val result = Array(height) { Array(width) { 0 } }

        this.forEach {
            result[it.pos.y][it.pos.x]++
        }

        return result
    }

    fun Array<Array<Int>>.display() : String {
        val sb = StringBuilder()
        for(y in 0 until this.size) {
            for (x in 0 until this[0].size) {
                sb.append(this[y][x])
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    fun Int.makePositive(len: Int): Int = ((this % len) + len) % len

    fun Robot.future(time: Int, height: Int, width: Int): Robot {
        val nextX = (this.pos.x + this.velocity.x*time).makePositive(width) % width
        val nextY = (this.pos.y + this.velocity.y*time).makePositive(height) % height
        return this.copy(pos = SimplePoint(nextX, nextY))
    }

    fun List<Robot>.future(time: Int, height: Int, width: Int): List<Robot> =
        this.map { it.future(time, height, width) }.toList()

    fun SimplePoint.quadtrant(height:Int, width: Int): Int {
        val wLimit = width / 2
        val hLimit = height / 2
        return when {
           this.x < wLimit && this.y < hLimit -> 0
           this.x > wLimit && this.y < hLimit -> 1
           this.x < wLimit && this.y > hLimit -> 2
           this.x > wLimit && this.y > hLimit -> 3
           else -> -1
        }
    }

    fun part1(input: List<String>, height: Int, width: Int): Int {
        val robots = parseInput(input)
        //robots.toField(height, width).display().println()
        val after100 = robots.future(100, height, width)
        //println("----------------------")
        //after100.toField(height, width).display().println()

        val quadrantCounts: Map<Int, Int> = after100.groupingBy { it.pos.quadtrant(height, width) }.eachCount()
        return quadrantCounts.filter { it.key >= 0 }.values.fold(1) { acc, count -> acc * count }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day14_test")
    val testPart1 = part1(testInput, 7 ,11)
    check(testPart1 == 12) { "Expected 12 but got $testPart1" }

    val input = readInput("Day14")
    val part1 = part1(input, 103, 101)
    part1.println()
    check(part1 == 221142636) { "Expected 221142636 but got $part1" }
    /*
        val testPart2 = part2(testInput)
        check(testPart2 == 1) { "Expected 1 but got $testPart2" }
        part2(input).println()
    */
}
