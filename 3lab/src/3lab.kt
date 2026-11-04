package example.myapp

import kotlin.math.PI

// 1. Classes, properties, constructors, init blocks and property accessors

open class Aquarium(
    var length: Int = 100,
    var width: Int = 20,
    open var height: Int = 40
) {
    // The secondary constructor demonstrates constructor delegation.
    constructor(numberOfFish: Int) : this() {
        // 2,000 cm^3 per fish, plus 10% extra room.
        val tankVolume = (numberOfFish * 2000 * 1.1).toInt()
        height = tankVolume / (width * length)
    }

    init {
        println("aquarium initializing")
    }

    init {
        // 1 liter = 1,000 cm^3.
        println("Volume: $volume liters")
    }

    open val volume: Int
        get() = width * length * height / 1000

    // In the course example this setter changes the aquarium's height.
    // It is public here so the setter example can be executed.
    var adjustableVolume: Int
        get() = volume
        set(value) {
            height = value * 1000 / (width * length)
        }

    open val shape: String
        get() = "rectangle"

    open val water: Double
        get() = volume * 0.9

    fun printSize() {
        println(shape)
        println(
            "Width: $width cm " +
                    "Length: $length cm " +
                    "Height: $height cm"
        )
        println("Volume: $volume liters")
        println("Water: $water liters")
    }
}

class TowerTank(
    override var height: Int,
    private val diameter: Int
) : Aquarium(height = height, width = diameter, length = diameter) {

    // A cylindrical tank has a circular base: π × r² × height.
    override val volume: Int
        get() = (PI * (diameter / 2.0) * (diameter / 2.0) * height / 1000).toInt()

    override val water: Double
        get() = volume * 0.8

    override val shape: String
        get() = "cylinder"
}

fun aquariumExamples() {
    println("\n--- Aquarium examples ---")

    // Default dimensions.
    val aquarium1 = Aquarium()
    aquarium1.printSize()

    // Named arguments and default values.
    val aquarium2 = Aquarium(width = 25)
    aquarium2.printSize()

    val aquarium3 = Aquarium(height = 35, length = 110)
    aquarium3.printSize()

    val aquarium4 = Aquarium(width = 25, height = 35, length = 110)
    aquarium4.printSize()

    // Changing a property.
    aquarium1.height = 60
    aquarium1.printSize()

    // Secondary constructor.
    val aquariumWithFish = Aquarium(numberOfFish = 29)
    aquariumWithFish.printSize()

    // Property setter changes the height to reach the requested volume.
    aquariumWithFish.adjustableVolume = 70
    aquariumWithFish.printSize()

    // Inheritance and overridden properties.
    val rectangularTank = Aquarium(width = 25, length = 25, height = 40)
    rectangularTank.printSize()

    val towerTank = TowerTank(diameter = 25, height = 40)
    towerTank.printSize()
}

// 2. Abstract classes, interfaces, objects and delegation

abstract class AquariumFish {
    abstract val color: String
}

interface FishAction {
    fun eat()
}

interface FishColor {
    val color: String
}

class Shark : AquariumFish(), FishAction {
    override val color = "grey"

    override fun eat() {
        println("hunt and eat fish")
    }
}

class SimplePlecostomus : AquariumFish(), FishAction {
    override val color = "gold"

    override fun eat() {
        println("eat algae")
    }
}

// A singleton object can provide a shared implementation.
object GoldColor : FishColor {
    override val color = "gold"
}

class PrintingFishAction(private val food: String) : FishAction {
    override fun eat() {
        println(food)
    }
}

// Interface delegation:
class Plecostomus(
    fishColor: FishColor = GoldColor
) : FishAction by PrintingFishAction("eat algae"),
    FishColor by fishColor

fun fishExamples() {
    println("\n--- Fish examples ---")

    val shark = Shark()
    val simplePleco = SimplePlecostomus()

    println("Shark: ${shark.color}")
    shark.eat()
    println("Plecostomus: ${simplePleco.color}")
    simplePleco.eat()

    val delegatedPleco = Plecostomus()
    println("Delegated plecostomus: ${delegatedPleco.color}")
    delegatedPleco.eat()
}

// 3. Data classes, equality, destructuring and enums

data class Decoration(val rocks: String)

data class Decoration2(
    val rocks: String,
    val wood: String,
    val diver: String
)

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

enum class Direction(val degrees: Int) {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270)
}

class Choice {
    companion object {
        var name: String = "lyric"

        fun showDescription(name: String) {
            println("My favorite $name")
        }
    }
}

fun dataClassAndEnumExamples() {
    println("\n--- Data classes and enums ---")

    val decoration1 = Decoration("granite")
    val decoration2 = Decoration("slate")
    val decoration3 = Decoration("slate")

    println(decoration1)
    println(decoration2)
    println(decoration3)
    println(decoration1 == decoration2)
    println(decoration3 == decoration2)

    val d5 = Decoration2("crystal", "wood", "diver")
    println(d5)

    // Destructuring declaration.
    val (rock, wood, diver) = d5
    println(rock)
    println(wood)
    println(diver)

    // Ignore the second component with an underscore.
    val (first, _, third) = d5
    println("$first, $third")

    println(Direction.EAST.name)
    println(Direction.EAST.ordinal)
    println(Direction.EAST.degrees)

    println(Choice.name)
    Choice.showDescription("pick")
    Choice.showDescription("selection")
}

// 4. Pairs, triples, collections, partition and maps

data class Fish(val name: String, val freshWater: Boolean)

fun isFreshWater(fish: Fish): Boolean = fish.freshWater

fun collectionExamples() {
    println("\n--- Collections, pairs, triples and maps ---")

    val fish = listOf(
        Fish("guppy", true),
        Fish("catfish", true),
        Fish("zebra fish", false)
    )

    val twoLists = fish.partition { isFreshWater(it) }
    println("freshwater: ${twoLists.first}")
    println("saltwater: ${twoLists.second}")

    val equipment = "fish net" to "catching fish"
    println("${equipment.first} used for ${equipment.second}")

    val numbers = Triple(6, 9, 42)
    println(numbers.toString())
    println(numbers.toList())

    val equipment2 = ("fish net" to "catching fish") to "equipment"
    println("${equipment2.first} is ${equipment2.second}")
    println(equipment2.first.second)

    val (tool, use) = equipment
    println("$tool is used for $use")

    val (n1, n2, n3) = numbers
    println("$n1 $n2 $n3")

    val numberList = listOf(1, 5, 3, 4)
    println(numberList.sum())

    val stringList = listOf("a", "bbb", "cc")
    // A list of strings cannot be summed directly. Sum their lengths instead.
    println(stringList.sumOf { it.length })

    for (s in stringList.listIterator()) {
        println(s)
    }

    val scientific = hashMapOf(
        "guppy" to "poecilia reticulata",
        "catfish" to "corydoras",
        "zebra fish" to "danio rerio"
    )

    println(scientific.get("guppy"))
    println(scientific.get("zebra fish"))
    println(scientific.get("swordtail"))
    println(scientific.getOrDefault("swordtail", "sorry, I don't know"))
    println(scientific.getOrElse("swordtail") { "sorry, I don't know" })
}

// 5. Constants and extension functions

const val ROCKS = 3

object Constants {
    const val CONSTANT2 = "object constant"
}

class MyClass {
    companion object {
        const val CONSTANT3 = "constant in companion"
    }
}

fun complexFunctionCall(): String = "result of a function call"

fun String.hasSpaces(): Boolean {
    val found = this.indexOf(' ')
    // indexOf returns an index, or -1 when the character is not found.
    return found != -1
}

// Shorter version of the same extension function.
fun String.hasSpacesShort(): Boolean = indexOf(" ") != -1

// 6. Extension properties, visibility and extension dispatch

open class AquariumPlant(
    val color: String,
    private val size: Int
)

fun AquariumPlant.isRed(): Boolean = color == "red"

class GreenLeafyPlant(size: Int) : AquariumPlant("green", size)

fun AquariumPlant.print() = println("AquariumPlant")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

val AquariumPlant.isGreen: Boolean
    get() = color == "green"

// Nullable receiver extension and safe-call.
fun AquariumPlant?.pull() {
    this?.apply {
        println("removing $this")
    }
}

fun extensionExamples() {
    println("\n--- Extension function examples ---")

    println("hello world".hasSpaces())
    println("helloworld".hasSpacesShort())

    val plant = GreenLeafyPlant(size = 10)
    plant.print()
    println()

    val aquariumPlant: AquariumPlant = plant
    // Extension functions are resolved from the declared receiver type.
    aquariumPlant.print()

    println("Is green: ${aquariumPlant.isGreen}")
    println("Is red: ${aquariumPlant.isRed()}")

    val nullablePlant: AquariumPlant? = null
    nullablePlant.pull()
}

fun main() {
    aquariumExamples()
    fishExamples()
    dataClassAndEnumExamples()
    collectionExamples()

    println("\n--- Constants ---")
    println(ROCKS)
    println(Constants.CONSTANT2)
    println(MyClass.CONSTANT3)
    println(complexFunctionCall())

    extensionExamples()
}