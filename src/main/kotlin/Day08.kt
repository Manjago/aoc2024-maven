/*
..........
...#......
..........
....a.....
..........
.....a....
..........
......#...
..........
..........


p1 3,4
p2 5,5

delta 2,1 (p2 - p1)

a1 = p1 - delta  1,3   pt - p2 + p1 = 2*p1 - p2
a2 = p2 + delta  7,6   p2 + p2 -p1 = 2*p2 - p1

----------------
0,0
1,3

2*p2 - p1 = 2,6
3*p2 - p1 = 3,9


T....#....
...T......
.T....#...
.........#
..#.......
..........
...#......
..........
....#.....
..........

 */

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
        for(key in data.keys) {
            val nodes = data[key]

            val pairs = mutableSetOf<NodePair>()

            for(i in nodes!!.indices) {
                for(j in nodes.indices) {
                    if (i == j) {
                        continue
                    }

                    val node1 = nodes[i]
                    val node2 = nodes[j]

                    when(node1.compareTo(node2)) {
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
        for(key in data.keys) {
            val nodes = data[key]

            val pairs = mutableSetOf<NodePair>()

            for(i in nodes!!.indices) {
                for(j in nodes.indices) {
                    if (i == j) {
                        continue
                    }

                    val node1 = nodes[i]
                    val node2 = nodes[j]

                    when(node1.compareTo(node2)) {
                        0 -> continue
                        -1 -> pairs.add(NodePair(node1, node2))
                        +1 -> pairs.add(NodePair(node2, node1))
                    }

                }
            }


            for (pair in pairs) {

                println("For pair $pair")

                antinodes.add(pair.left)
                antinodes.add(pair.right)

                var index = 2
                var pretender = pair.left * index - pair.right
                while(pretender.inBound(width, height)) {
                    antinodes.add(pretender)
                    println("1 add $pretender")
                    index++
                    pretender = pair.left * index - pair.right
                }

                index = 2
                pretender = pair.right * index - pair.left
                while(pretender.inBound(width, height)) {
                    antinodes.add(pretender)
                    println("2 add $pretender")
                    index++
                    pretender = pair.right * index - pair.left
                }
            }
        }

        println(antinodes)
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
    /*
        part2(input).println()
    */

    /*
T....#....  SimplePoint(x=5, y=0)  2*3 - 1, 2*1 - 2
...T......
.T....#...
.........#
..#.......
..........
...#......
..........
....#.....
..........

E....E....
...E......
.E....E...
.........E
..E.......
..........
...E......
..........
....E.....
..........

SimplePoint(x=0, y=0) ok
SimplePoint(x=3, y=1) ok
SimplePoint(x=6, y=2) ok
SimplePoint(x=9, y=3) ok
SimplePoint(x=1, y=2) ok
SimplePoint(x=2, y=4) ok
SimplePoint(x=3, y=6) ok
SimplePoint(x=4, y=8) ok
SimplePoint(x=5, y=0) ok
SimplePoint(x=8, y=1) ??

delta p2 - p1

p1 + delta, p1 + 2*detla ...
p2 - delta, p2 - 2*delta ...

     */
}
