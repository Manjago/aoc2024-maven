package day01

import kotlin.math.abs

fun day01Part1(input: List<String>): Int {
    val data = readInput(input)

    var dist = 0
    val deleted0 = Array<Boolean>(data.size) { false }
    val deleted1 = Array<Boolean>(data.size) { false }
    repeat(data.size) {
        var index0 = -1
        var index1 = -1
        var min0 = Integer.MAX_VALUE
        var min1 = Integer.MAX_VALUE
        for (index in data.indices) {
            val item0 = data[index][0]
            val item1 = data[index][1]
            if (!deleted0[index] && item0 < min0) {
                min0 = item0
                index0 = index
            }
            if (!deleted1[index] && item1 < min1) {
                min1 = item1
                index1 = index
            }
        }

        //println("min0=$min0,min1=$min1,index0=$index0,index1=$index1")
        val delta = abs(min0 - min1)
        //println("delta=$delta")
        dist += delta
        deleted0[index0] = true
        deleted1[index1] = true
    }

    return dist
}

fun day01Part2(input: List<String>): Int {
    val data: List<List<Int>> = readInput(input)
    val mapa = mutableMapOf<Int, Int>()
    data.forEach {
        val key = it[1]
        if (mapa.containsKey(key)) {
            mapa[key] = mapa[key]!!.plus(1)
        } else {
            mapa[key] = 1
        }
    }

    var score = 0
    data.forEach {
        val item = it[0]
        if (mapa.containsKey(item)) {
            val add = mapa[item]!!
            var rewritedAdd = item * add
            score += rewritedAdd
            //println("For item $item addition $add, rewritedAdd $rewritedAdd, total score: $score")
        } //else {
            //println("For item $item skipping $score, total score: $score")
        //}
    }

    return score
}

private fun readInput(input: List<String>): List<List<Int>> = input.asSequence().map { it.split("   ") }
    .map {
        listOf(it[0].toInt(), it[1].toInt())
    }.toList()