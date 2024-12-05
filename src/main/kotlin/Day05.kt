fun main() {

    data class Rule(val prev: Int, val next: Int)

    class Rules(private val rules: MutableList<Rule> = mutableListOf()) {

        fun parseAndAddRule(s: String) {
            val tokens = s.split("|")
            rules.add(Rule(tokens[0].toInt(), tokens[1].toInt()))
        }

        fun pagesAfter(page: Int) : Sequence<Int> = rules.asSequence().filter{it.prev == page}.map{it.next}
        fun pagesBefore(page: Int) : Sequence<Int> = rules.asSequence().filter{it.next == page}.map{it.next}
    }

    fun part1(input: List<String>): Int {

        val rules = Rules()
        val updates: MutableList<List<Int>> = mutableListOf()

        for (s in input) {
            when {
                s.contains("|") -> rules.parseAndAddRule(s)
                s.contains(",") -> updates.add(s.split(",").asSequence().map { it.toInt() }.toList())
            }
        }

        var answer = 0

        for (update in updates) {
            var badFound = false

            for(i in 0..update.lastIndex) {
                val page = update[i]
                if (rules.pagesAfter(page).any { next ->
                        val pos = update.indexOf(next)
                        pos >= 0 && pos < i
                    }) {
                    badFound = true
                    break
                }

                if (rules.pagesBefore(page).any { prev ->
                        val pos = update.indexOf(prev)
                        pos >= 0 && pos > i
                    }) {
                    break
                }

            }

            if (!badFound) {
                debug("found $update")
                answer += update[update.size / 2]
            }

        }

        return answer
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day05_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 143) { "Expected 143 but got $testPart1" }

    val input = readInput("Day05")
    part1(input).println()
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
}
