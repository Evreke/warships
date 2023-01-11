/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke

import kotlin.random.Random

class Game {
    var field: Field

    init {
        field = initField()
    }

    companion object {
        private const val fieldSize = 10
        private val positionResolver = PositionResolver()
    }

    private fun initField(): Field {
        val list = mutableListOf<MutableList<Int?>>()
        for (i in 0 until fieldSize) {
            list.add(MutableList(fieldSize) { null })
        }
        return Field(fieldSize, list)
    }

    fun placeShip(coords: Coords, ship: Ship): Boolean {
        // Checks before place
        when {
            isOutOfBounds(coords, ship) -> {
                //TODO Implement answer to interpret
                return false
            }

            hasProhibitedCellsOnPath(coords, ship) -> {
                //TODO Implement answer to interpret
                return false
            }
        }

        return field.placeShip(coords, ship) {
            positionResolver.resolvePosition(coords, fieldSize - 1)
        }
    }

    private fun isOutOfBounds(coords: Coords, ship: Ship): Boolean =
        positionResolver.isOutOfBounds(coords, ship, fieldSize)

    private fun hasProhibitedCellsOnPath(coords: Coords, ship: Ship): Boolean {
        when (ship.direction) {
            Direction.HORIZONTAL -> {
                for (i in 0 until ship.size) {
                    if (field.field[coords.y][coords.x + i] == 1 || field.field[coords.y][coords.x + i] != null) {
                        println("cannot place ship. Prohibited position")
                        return true
                    }
                }
                return false
            }

            Direction.VERTICAL -> {
                for (i in 0 until ship.size) {
                    if (field.field[coords.y + i][coords.x] == 1 || field.field[coords.y + i][coords.x] != null) {
                        println("cannot place ship. Prohibited position")
                        return true
                    }
                }
                return false
            }
        }
    }
}

fun Field.initShips() {
    val ships = mutableListOf(
        SmallShip(direction = Direction.HORIZONTAL),
        SmallShip(direction = Direction.HORIZONTAL),
        SmallShip(direction = Direction.VERTICAL),
        SmallShip(direction = Direction.HORIZONTAL),
        MediumShip(direction = Direction.VERTICAL),
        MediumShip(direction = Direction.HORIZONTAL),
        MediumShip(direction = Direction.HORIZONTAL),
        BigShip(direction = Direction.VERTICAL),
        BigShip(direction = Direction.HORIZONTAL),
        HugeShip(direction = Direction.VERTICAL),
    )

    while (ships.size != 0) {
        val shipToPlace = ships.removeAt(Random.nextInt(0, ships.size))

        var shipPlaced = false
        while (!shipPlaced) {
            // TODO Try to place ships in O(n).
            val x = Random.nextInt(0, size - 2)
            val y = Random.nextInt(0, size - 2)
            val coords = Coords(x, y)
            placeShip(coords, shipToPlace) { PositionResolver().resolvePosition(coords, field.size - 1) }.also {
                shipPlaced = it
            }
        }
    }
}