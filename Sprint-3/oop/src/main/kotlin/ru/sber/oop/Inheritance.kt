package ru.sber.oop

open class Room(val name: String, val size: Int) {
    constructor(name: String) : this(name, 100)

    protected open val dangerLevel = 5

    fun description() = "Room: $name"


    val larryTheGoblin: Monster = Goblin(
        "Larry",
        "Adorable goblin who likes bachata dance",
        "Pretty Weak",
        150
    )

    fun Monster.getSalutation(name: String): String {
        return "$name, salute, my dear fella! "
    }

    open fun load() = larryTheGoblin.getSalutation(name) +
            "Nothing much to see here..."
}

class TownSquare : Room("Town Square", 1000) {

    final override fun load() = "Wish we'd been alive\n" +
            "Before the world was bright\n" +
            "I would've painted you in the town square\n" +
            "Under a purple sky"

    override val dangerLevel = super.dangerLevel - 3

}
