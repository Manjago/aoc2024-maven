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

    fun parseInput(input: List<String>) : Pair<Rules, List<List<Int>>> {
        val rules = Rules()
        val updates: MutableList<List<Int>> = mutableListOf()

        for (s in input) {
            when {
                s.contains("|") -> rules.parseAndAddRule(s)
                s.contains(",") -> updates.add(s.split(",").asSequence().map { it.toInt() }.toList())
            }
        }
        return Pair(rules, updates)
    }

    fun corePart1(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {

        val (rules, updates) = parseInput(input)

        val good = mutableListOf<List<Int>>()
        val bad = mutableListOf<List<Int>>()

        for (update in updates) {
            var badFound = false

            for(i in 0..update.lastIndex) {
                val page = update[i]
                if (rules.pagesAfter(page).any { next ->
                        val pos = update.indexOf(next)
                        pos >= 0 && pos < i
                    }) {
                    badFound = true
                    bad.add(update)
                    break
                }

                if (rules.pagesBefore(page).any { prev ->
                        val pos = update.indexOf(prev)
                        pos >= 0 && pos > i
                    }) {
                    bad.add(update)
                    break
                }

            }

            if (!badFound) {
                debug("found $update")
                good.add(update)
            }

        }

        return good to bad
    }

    fun part1(input: List<String>): Int {
        val (good, _) = corePart1(input)
        return good.asSequence().map { it[it.size / 2] }.sum()
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day05_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 143) { "Expected 143 but got $testPart1" }

    val input = readInput("Day05")
    val part1 = part1(input)
    part1.println()
    check(part1 == 4924) { "Expected 4924 but got $part1" }
    val testPart2 = part2(testInput)
    check(testPart2 == 1) { "Expected 1 but got $testPart2" }
    part2(input).println()
}
