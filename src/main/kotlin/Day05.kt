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

    fun parseInput(input: List<String>) : Pair<Rules, List<MutableList<Int>>> {
        val rules = Rules()
        val updates: MutableList<MutableList<Int>> = mutableListOf()

        for (s in input) {
            when {
                s.contains("|") -> rules.parseAndAddRule(s)
                s.contains(",") -> updates.add(s.split(",").asSequence().map { it.toInt() }.toMutableList())
            }
        }
        return Pair(rules, updates)
    }

    fun isGoodByRules(update: List<Int>, rules: Rules): Boolean {
        for (i in 0..update.lastIndex) {
            val page = update[i]
            if (rules.pagesAfter(page).any { next ->
                    val pos = update.indexOf(next)
                    pos >= 0 && pos < i
                }) {
                return false
            }

            if (rules.pagesBefore(page).any { prev ->
                    val pos = update.indexOf(prev)
                    pos >= 0 && pos > i
                }) {
                return false
            }
        }
        return true
    }

    fun sortByGoodAndBad(input: List<String>): Triple<List<MutableList<Int>>, List<MutableList<Int>>, Rules> {

        val (rules, updates) = parseInput(input)

        val good = mutableListOf<MutableList<Int>>()
        val bad = mutableListOf<MutableList<Int>>()

        for (update in updates) {
            if (isGoodByRules(update, rules)) {
                good.add(update)
            } else {
                bad.add(update)
            }
        }

        return Triple(good, bad, rules)
    }

    fun part1(input: List<String>): Int {
        val (good, _) = sortByGoodAndBad(input)
        return good.asSequence().map { it[it.size / 2] }.sum()
    }

    fun MutableList<Int>.swap(i: Int, j: Int) {
        val t = this[i]
        this[i] = this[j]
        this[j] = t
    }

    fun part2(input: List<String>): Int {
        val (_, bad, rules) = sortByGoodAndBad(input)

        bad.forEach { b ->

             while(!isGoodByRules(b, rules)) {

                 for(i in 0 ..b.lastIndex) {
                     val page = b[i]

                     val badNext = rules.pagesAfter(page).find { next ->
                         val pos = b.indexOf(next)
                         pos >= 0 && pos < i
                     }
                     val badNextIndex = if (badNext != null) { b.indexOf(badNext) } else null
                     if (badNextIndex != null) {
                         b.swap(i, badNextIndex)
                     }
                 }

             }

             while(!isGoodByRules(b, rules)) {

                 for(i in 0 ..b.lastIndex) {
                     val page = b[i]

                     val badPrev = rules.pagesBefore(page).find { prev ->
                         val pos = b.indexOf(prev)
                         pos >= 0 && pos > i
                     }
                     val badPrevIndex = if (badPrev != null) { b.indexOf(badPrev) } else null
                     if (badPrevIndex != null) {
                         b.swap(i, badPrevIndex)
                     }
                 }

             }

        }

        return bad.asSequence().map { it[it.size / 2] }.sum()
    }

    val testInput = readInput("Day05_test")
    val testPart1 = part1(testInput)
    check(testPart1 == 143) { "Expected 143 but got $testPart1" }

    val input = readInput("Day05")
    val part1 = part1(input)
    part1.println()
    check(part1 == 4924) { "Expected 4924 but got $part1" }
    val testPart2 = part2(testInput)
    check(testPart2 == 123) { "Expected 123 but got $testPart2" }
    part2(input).println()
}
