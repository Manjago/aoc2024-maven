fun main() {

    val LEFT = SimplePoint(-1, 0)
    val LOWER = SimplePoint(0, 1)
    val RIGHT = SimplePoint(1, 0)
    val UPPER = SimplePoint(0, -1)

    val directions = listOf(
        RIGHT, LEFT, LOWER, UPPER
    )

    val horizontal = listOf(
        RIGHT, LEFT
    )
    val vertical = listOf(
        LOWER, UPPER
    )


    fun parseInput(input: List<String>): Array<Array<Char>> {
        val result = Array<Array<Char>>(input.size) { Array(input[0].length) { '.' } }
        for (y in input.indices) {
            for (x in input[y].indices) {
                result[y][x] = input[y][x]
            }
        }
        return result
    }

    fun Array<Array<Char>>.isSameChar(myChar: Char, pretender: SimplePoint): Boolean =
        pretender.inBound(this[0].size, size) && this[pretender.y][pretender.x] == myChar

    fun Array<Array<Char>>.flood(point: SimplePoint, seen: MutableSet<SimplePoint>): Set<SimplePoint> {

        val queue = ArrayDeque<SimplePoint>()
        queue.add(point)
        val myChar = this[point.y][point.x]
        val region = mutableSetOf(point)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            for (dir in directions) {
                val pretender = current + dir
                if (seen.contains(pretender)) {
                    continue
                }
                if (isSameChar(myChar, pretender)) {
                    region.add(pretender)
                    seen.add(pretender)
                    queue += pretender
                }
            }
        }

        return region
    }

    fun Array<Array<Char>>.perimeter(region: Set<SimplePoint>): Long {
        var result = 0L
        for (point in region) {
            val myChar = this[point.y][point.x]
            for (dir in directions) {
                val pretender = point + dir
                if (!isSameChar(myChar, pretender)) {
                    ++result
                }
            }
        }
        return result
    }

    fun Array<Array<Char>>.corners(region: Set<SimplePoint>): Long {
        var result = 0L
        for (p in region) {
            val myChar = this[p.y][p.x]
            for (hor in horizontal) {
                for (vert in vertical) {
                    if (!isSameChar(myChar, p + hor) && !isSameChar(myChar, p + vert) ||
                        !isSameChar(myChar,p + vert + hor) && isSameChar(myChar, p + vert) && isSameChar(myChar, p + hor)
                    ) {
                        ++result
                    }
                }
            }
        }
        return result
    }

    fun Array<Array<Char>>.makeRegions(): List<Set<SimplePoint>> {
        val seen = mutableSetOf<SimplePoint>()
        val regions = mutableListOf<Set<SimplePoint>>()

        for (y in this.indices) {
            for (x in this[y].indices) {
                val point = SimplePoint(x, y)
                if (seen.contains(point)) {
                    continue
                }
                regions.add(flood(point, seen))
            }
        }
        return regions.toList()
    }

    fun part1(input: List<String>): Long {
        val data = parseInput(input)
        val regions = data.makeRegions()

        val score = regions.asSequence().map {
            val p = data.perimeter(it)
            val s = it.size
            s * p
        }.sum()
        return score
    }

    fun part2(input: List<String>): Long {
        val data = parseInput(input)
        val regions = data.makeRegions()

        val score = regions.asSequence().map {
            val p = data.corners(it)
            val s = it.size
            s * p
        }.sum()
        return score
    }

    val testInput1 = readInput("Day12_test1")
    val testPart1_1 = part1(testInput1)
    check(testPart1_1 == 140L) { "Expected 140 but got $testPart1_1" }

    val testInput2_1 = readInput("Day12_test2")
    val testPart1_2 = part1(testInput2_1)
    check(testPart1_2 == 772L) { "Expected 772 but got $testPart1_2" }

    val testInput3_1 = readInput("Day12_test3")
    val testPart1_3 = part1(testInput3_1)
    check(testPart1_3 == 1930L) { "Expected 1930 but got $testPart1_3" }

    val input = readInput("Day12")
    val part1 = part1(input)
    part1.println()
    check(part1 == 1363484L) { "Expected 1930 but got $part1" }

    val testPart2 = part2(testInput3_1)
    check(testPart2 == 1206L) { "Expected 1206 but got $testPart2" }

    val part2 = part2(input)
    part2.println()
    check(part2 == 838988L) { "Expected 838988 but got $testPart2" }
}
