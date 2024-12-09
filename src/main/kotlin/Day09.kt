import java.util.*

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

    data class Block(val id: Int, val len: Int)

    fun parseInput2(compact: String): LinkedList<Block> {
         val result = LinkedList<Block>()

        var id = 0
        for (i in 0 until compact.length) {
            val blockSize = compact[i].digitToInt()
            if (i % 2 == 0) {
                if (blockSize > 0) {
                    result.addLast(Block(id++, blockSize))
                }
            } else {
                if (blockSize > 0) {
                    result.addLast(Block(-1, blockSize))
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

    fun LinkedList<Block>.displayArray(): Array<Int> {
        val len = this.asSequence().sumOf{ it.len }
        val result = Array(len) { 0 }
        var index = 0
        for(item in this) {
            val len = item.len
            repeat(len) { result[index++] = item.id }
        }

        return result
    }

    fun part2(input: List<String>): Long {
        //val workData0 = parseInput(input[0])
        val workData = parseInput2(input[0])

        //println(Arrays.toString(workData0))
        //println(workData)
        //println(Arrays.toString(workData.displayArray()))
        //println("----------------")

        val maxId = workData.asSequence().maxOf { it.id }
        val blockIndex = Array<Block>(maxId + 1) { Block(0, 0) }
        for(item in workData) {
            if (item.id != -1) {
                blockIndex[item.id] = item
            }
        }

        for (id in maxId downTo 0) {
            //println("Start $id")
            val indexOfBlock = workData.lastIndexOf(blockIndex[id])
            var freeIndex = -1

            for(i in 0..workData.size - 1) {
                val free = workData[i]
                if (free.id != -1) {
                    continue
                }

                if (free.len < blockIndex[id].len) {
                    continue
                }

                freeIndex = i

                break
            }

            if (freeIndex != -1 && freeIndex < indexOfBlock) {

                //println("For $id found ${workData[freeIndex].len} at index $freeIndex")
                //println("From ${Arrays.toString(workData.displayArray())}")

                val freeDiff = workData[freeIndex].len - workData[indexOfBlock].len

                val temp = workData[freeIndex]
                workData[freeIndex] = workData[indexOfBlock]
                workData[indexOfBlock] = temp

                // now freeIndex = block, indexOfBlock = free

                if (freeDiff > 0) {
                    workData[indexOfBlock] = workData[indexOfBlock].copy(len = workData[indexOfBlock].len - freeDiff)
                    val newFreeBlock = Block(-1, freeDiff)
                    workData.add(freeIndex+1, newFreeBlock)
                }

                //println("Now ${Arrays.toString(workData.displayArray())}")
                //println("----++++-----")

            }

            //println("Finni $id")
        }

        //println(workData)
        val displayArray = workData.displayArray()
        //println(Arrays.toString(displayArray))

        var answer = 0L
        for (pos in 0 until displayArray.size) {
            val id = displayArray[pos]
            if (id != -1) {
                answer += id * pos
            }
        }

        return answer
    }

    val testInput = readInput("Day09_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 1928L) { "Expected 1928 but got $testPart1" }

    val input = readInput("Day09")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 2858L) { "Expected 2858 but got $testPart2" }
    part2(input).println()
}
