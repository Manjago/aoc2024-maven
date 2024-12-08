fun main() {

    data class NodePair(val left: SimplePoint, val right: SimplePoint)

    fun parseInput(input: List<String>): MutableMap<Char, MutableList<SimplePoint>> {
        val result = mutableMapOf<Char, MutableList<SimplePoint>>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                val c = input[y][x]
                if (c == '.') {
                    continue
                }

                if (!result.containsKey(c)) {
                    result.put(c, mutableListOf(SimplePoint(x, y)))
                } else {
                    result[c]!!.add(SimplePoint(x, y))
                }

            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)
        val height = input.size
        val width = input[0].length

        val antinodes = mutableSetOf<SimplePoint>()
        for (key in data.keys) {
            val nodes = data[key]

            val pairs = mutableSetOf<NodePair>()

            for (i in nodes!!.indices) {
                for (j in nodes.indices) {
                    if (i == j) {
                        continue
                    }

                    val node1 = nodes[i]
                    val node2 = nodes[j]

                    when (node1.compareTo(node2)) {
                        0 -> continue
                        -1 -> pairs.add(NodePair(node1, node2))
                        +1 -> pairs.add(NodePair(node2, node1))
                    }

                }
            }

            for (pair in pairs) {
                val antinode1 = pair.left * 2 - pair.right
                val antinode2 = pair.right * 2 - pair.left
                if (antinode1.inBound(width, height)) {
                    antinodes.add(antinode1)
                }
                if (antinode2.inBound(width, height)) {
                    antinodes.add(antinode2)
                }
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val data = parseInput(input)
        val height = input.size
        val width = input[0].length

        val antinodes = mutableSetOf<SimplePoint>()
        for (key in data.keys) {
            val nodes = data[key]

            val pairs = mutableSetOf<NodePair>()

            for (i in nodes!!.indices) {
                for (j in nodes.indices) {
                    if (i == j) {
                        continue
                    }

                    val node1 = nodes[i]
                    val node2 = nodes[j]

                    when (node1.compareTo(node2)) {
                        0 -> continue
                        -1 -> pairs.add(NodePair(node1, node2))
                        +1 -> pairs.add(NodePair(node2, node1))
                    }

                }
            }


            for (pair in pairs) {

                /*
                delta p2 - p1

                p1 + delta, p1 + 2*detla ...
                p2 - delta, p2 - 2*delta ...
                 */
                val delta = pair.right - pair.left
                var counter = 0
                var pretender = pair.left + delta * counter
                while (pretender.inBound(width, height)) {
                    antinodes.add(pretender)
                    counter++
                    pretender = pair.left + delta * counter
                }

                counter = 0
                pretender = pair.right - delta * counter
                while (pretender.inBound(width, height)) {
                    antinodes.add(pretender)
                    counter++
                    pretender = pair.right - delta * counter
                }
            }
        }

        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 14) { "Expected 14 but got $testPart1" }

    val input = readInput("Day08")
    val part1 = part1(input)
    part1.println()
    check(part1 == 379) { "Expected 14 but got $part1" }


    val testInput2 = readInput("Day08_test2")
    val testPart2 = part2(testInput2)
    check(testPart2 == 9) { "Expected 9 but got $testPart2" }

    val testInput20 = readInput("Day08_test")
    val testPart20 = part2(testInput20)
    check(testPart20 == 34) { "Expected 34 but got $testPart2" }

    val part2 = part2(input)
    part2.println()
    check(part2 == 1339) { "Expected 1339 but got $part2" }

}
