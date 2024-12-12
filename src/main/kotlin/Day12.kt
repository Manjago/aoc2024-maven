fun main() {

    val directions = listOf(
        SimplePoint(1, 0), SimplePoint(-1, 0), SimplePoint(0, 1), SimplePoint(0, -1)
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

    fun flood(id: Int, point: SimplePoint, seen: MutableSet<SimplePoint>, data: Array<Array<Char>>): Set<SimplePoint> {

        val height = data.size
        val width = data[0].size
        val queue = ArrayDeque<SimplePoint>()
        queue.add(point)
        val myChar = data[point.y][point.x]
        val region = mutableSetOf(point)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            for (dir in directions) {
                val pretender = current + dir
                if (seen.contains(pretender)) {
                    continue
                }
                if (pretender.inBound(width, height)) {
                    val hisChar = data[pretender.y][pretender.x]
                    if (hisChar == myChar) {
                        region.add(pretender)
                        seen.add(pretender)
                        queue += pretender
                    }
                }
            }
        }

        return region
    }

    fun perimeter(region: Set<SimplePoint>, data: Array<Array<Char>>): Long {
        var result = 0L
        val height = data.size
        val width = data[0].size
        for (point in region) {
            val myChar = data[point.y][point.x]
            for (dir in directions) {
                val pretender = point + dir
                if (!pretender.inBound(width, height)) {
                    ++result
                } else {
                    val hisChar = data[pretender.y][pretender.x]
                    if (myChar != hisChar) {
                        ++result
                    }
                }
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {

        val data = parseInput(input)
        val seen = mutableSetOf<SimplePoint>()
        var id = 0
        val regions = mutableMapOf<Int, Set<SimplePoint>>()

        for (y in data.indices) {
            for (x in data[y].indices) {
                val point = SimplePoint(x, y)
                if (seen.contains(point)) {
                    continue
                }
                val region = flood(id, point, seen, data)
                regions.put(id++, region)
            }
        }

        val score = regions.values.asSequence().map {
            val p = perimeter(it, data)
            val s = it.size
            s * p
        }.sum()
        return score
    }

    fun part2(input: List<String>): Int {
        return 1
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
}
