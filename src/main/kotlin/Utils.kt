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

fun debug(s: String) {if (false) println(s)}

enum class Direction(internal val x: Int, internal val y: Int) {
    NORTH(0, -1), WEST(-1, 0), EAST(1, 0), SOUTH(0, 1)
}

data class SimplePoint(val x: Int, val y: Int)

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

