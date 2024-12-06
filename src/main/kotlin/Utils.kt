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

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
}

