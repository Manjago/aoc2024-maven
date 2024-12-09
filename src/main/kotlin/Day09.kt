fun main() {

    fun parseInput(compact: String): Array<Int> {
        val size = compact.toCharArray().asSequence().map { it.digitToInt() }.sum()
        val result = Array<Int>(size) { 0 }
        var id = 0
        var index = 0
        for (i in 0 until compact.length) {
            val blockSize = compact[i].digitToInt()
            if (i % 2 == 0) {
                repeat(blockSize) {
                    result[index++] = id
                }
                id++
            } else {
                repeat(blockSize) {
                    result[index++] = -1
                }
            }
        }

        return result
    }

    fun Array<Int>.swap(i: Int, j: Int) {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
    }

    fun part1(input: List<String>): Long {
        val workData = parseInput(input[0])

        var indexFree = 0
        while (workData[indexFree] != -1) {
            indexFree++
            if (indexFree >= workData.size) {
                break
            }
        }

        var indexBlock = workData.size - 1
        while (workData[indexBlock] == -1) {
            indexBlock--
            if (indexBlock < 0) {
                break
            }
        }

        if (indexBlock < 0 || indexFree >= workData.size) {
            throw IllegalStateException("Bad data")
        }

        while (indexBlock > indexFree) {

            workData.swap(indexBlock, indexFree)

            while (workData[indexFree] != -1) {
                indexFree++
                if (indexFree >= workData.size) {
                    break
                }
            }

            while (workData[indexBlock] == -1) {
                indexBlock--
                if (indexBlock < 0) {
                    break
                }
            }
        }

        var answer = 0L
        for (pos in 0 until workData.size) {
            val id = workData[pos]
            if (id != -1) {
                answer += id * pos
            }
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day09_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 1928L) { "Expected 1928 but got $testPart1" }

    val input = readInput("Day09")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    /*
    part2(input).println()
    */
}
