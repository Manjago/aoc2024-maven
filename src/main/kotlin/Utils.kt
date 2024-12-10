import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun debug(s: String) {if (true) println(s)}

enum class Direction(internal val x: Int, internal val y: Int) {
    NORTH(0, -1), WEST(-1, 0), EAST(1, 0), SOUTH(0, 1)
}

data class SimplePoint(val x: Int, val y: Int) : Comparable<SimplePoint> {
    fun inBound(width: Int, height: Int): Boolean = x in 0..width - 1 && y in 0..height - 1
    override fun compareTo(other: SimplePoint): Int = when {
        x != other.x -> x.compareTo(other.x)
        else -> y.compareTo(other.y)
    }
    operator fun times(other: Int) = SimplePoint(x * other, y * other)
    operator fun minus(other: SimplePoint) = SimplePoint(x - other.x, y - other.y)
    operator fun plus(other: SimplePoint) = SimplePoint(x + other.x, y + other.y)
}

data class OrientedPoint(val x: Int, val y: Int, val direction: Direction) {
    fun turnRight(): OrientedPoint = when (direction) {
        NORTH -> copy(direction = EAST)
        WEST -> copy(direction = NORTH)
        EAST -> copy(direction = SOUTH)
        SOUTH -> copy(direction = WEST)
    }
    fun step(): OrientedPoint = OrientedPoint(x + direction.x, y + direction.y, direction)
    fun toSimplePoint() = SimplePoint(x, y)
}

data class IntValuePoint(val x: Int, val y: Int, val value: Int)

