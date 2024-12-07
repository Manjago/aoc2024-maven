import java.math.BigInteger

fun main() {

    fun Array<Char>.next() : Boolean {
        var counter = 0
        while(counter < this.size) {
            when(this[counter]) {
              '+' -> {this[counter] = '*'; return true}
              '*' -> {this[counter] = '+'; counter++}
            }
        }
        return false
    }

    data class SingleEquation(val expected: BigInteger, val items: List<BigInteger>) {
        fun checkMe(): Boolean {

            val instruction = Array<Char>(items.size - 1) {'+'}

            do{
                var index = 0
                var result = BigInteger.ZERO
                for(i in items.indices){

                    if (i == 0) {
                        result = items[i]
                    } else {
                        val item = items[i]
                        val instr =instruction[index++]
                        when(instr) {
                            '+' -> result += item
                            '*' -> result *= item
                        }
                    }
                }

                if (result == expected) {
                    return true
                }

            }while(instruction.next())

            return false
        }
    }



    fun parseInput(input: List<String>): List<SingleEquation> = input.asSequence().map {
        val tokens  = it.split(":")
        val expected = tokens[0].toBigInteger()
        val items = tokens[1].trim().split(" ").asSequence().map {
            it.toBigInteger()
        }.toList()
        SingleEquation(expected, items)
    }.toList()

    fun part1(input: List<String>): BigInteger {
        val equations = parseInput(input)
        var answer = BigInteger.ZERO
        for(equation in equations) {
            if (equation.checkMe()) {
                answer += equation.expected
            }
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        return 1
    }


    val testInput = readInput("Day07_test")
    val testPart1 = part1(testInput)
    check(testPart1 == BigInteger.valueOf(3749L)) { "Expected 3749 but got $testPart1" }

    val input = readInput("Day07")
    val part1 = part1(input)
    part1.println()
    check(part1 == BigInteger.valueOf(12553187650171L)) { "Expected 12553187650171 but got $part1" }
    /*
        val testPart2 = part2(testInput)
        check(testPart2 == 1) { "Expected 1 but got $testPart2" }
        part2(input).println()
    */
}
