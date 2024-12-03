import Day3State.WAIT_FIRST_DIGIT
import Day3State.WAIT_L
import Day3State.WAIT_M
import Day3State.WAIT_OPEN_BRACKET
import Day3State.WAIT_SECOND_DIGIT
import Day3State.WAIT_U
import java.math.BigInteger

enum class Day3State {
    WAIT_M, WAIT_U, WAIT_L, WAIT_OPEN_BRACKET, WAIT_FIRST_DIGIT, WAIT_SECOND_DIGIT,
}

fun main() {

    var stateEnabled = true

    fun parseAndCalc(input: String, withSkip: Boolean = false): BigInteger {
        var answer = BigInteger.ZERO
        var state = WAIT_M
        val chars = input.toCharArray()

        val firstNum = StringBuilder()
        val secondNum = StringBuilder()

        for (i in chars.indices) {
            val char = chars[i]

            if (withSkip) {
                when (char) {
                    'а' -> stateEnabled = true
                    'б' -> stateEnabled = false
                }
            }

            val oldState = state
            when (state) {
                WAIT_M -> if ('m' == char) state = WAIT_U
                WAIT_U -> state = if ('u' == char) WAIT_L else WAIT_M
                WAIT_L -> state = if ('l' == char) WAIT_OPEN_BRACKET else WAIT_M
                WAIT_OPEN_BRACKET -> state = if ('(' == char) {
                    firstNum.clear(); WAIT_FIRST_DIGIT
                } else WAIT_M

                WAIT_FIRST_DIGIT -> state = when {
                    char.isDigit() -> {
                        firstNum.append(char); WAIT_FIRST_DIGIT
                    }

                    ',' == char -> {
                        secondNum.clear(); WAIT_SECOND_DIGIT
                    }


                    else -> WAIT_M
                }

                WAIT_SECOND_DIGIT -> state = when {
                    char.isDigit() -> {
                        secondNum.append(char)
                        WAIT_SECOND_DIGIT
                    }

                    ')' == char -> {
                        val first = firstNum.toString().toBigInteger()
                        val second = secondNum.toString().toBigInteger()
                        val delta = first * second
                        val prevAnser = answer
                        if (stateEnabled) {
                            answer += delta
                        }
                        debug("enabled = $stateEnabled, $first * $second = $delta, $prevAnser -> $answer")
                        WAIT_M
                    }

                    else -> WAIT_M
                }
            }
            debug("For char $char $oldState -> $state, first $firstNum, second $secondNum")
        }
        return answer
    }

    fun part1(input: List<String>): BigInteger = input.asSequence().map { string ->
        parseAndCalc(string)
    }.sumOf { it }

    fun part2(input: List<String>): BigInteger = input.asSequence().map { string -> string.replace("do()", "а") }
        .map { string -> string.replace("don't()", "б") }.map { string -> parseAndCalc(string, true) }.sumOf { it }

    val testInput = readInput("Day03_test")
    val testPart1 = part1(testInput)
    check(testPart1 == BigInteger.valueOf(161L)) { "Expected 161 but got $testPart1" }

    val input = readInput("Day03")
    part1(input).println()  // 153469856

    val testInput2 = readInput("Day03_2_test")

    val testPart2 = part2(testInput2)
    check(testPart2 == BigInteger.valueOf(48L)) { "Expected 48 but got $testPart2" }
    part2(input).println() //77055967

}

