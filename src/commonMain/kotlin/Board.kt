/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

import kotlinx.serialization.Serializable

data class Coords(
    val x: Int,
    val y: Int,
)

@Serializable
data class Board(
    val size: Int = 10,
    val field: MutableList<MutableList<Int>>
) {

    fun placeShip(coords: Coords, ship: Ship) {
        // Checks before place
        when {
            isOutOfField(coords, ship) -> {
                throw RuntimeException("Ship couldn't be placed outside of field")
            }

            hasProhibitedCellsOnPath(coords, ship) -> {
                throw RuntimeException("Ship placement rules violated")
            }
        }

        placeShipInternal(coords, ship)
    }

    private fun isOutOfField(coords: Coords, ship: Ship): Boolean {
        if (coords.x > field.size
            || coords.y > field.size
            || coords.x < 0
            || coords.y < 0
        ) {
            return true
        }

        return when (ship.direction) {
            Direction.HORIZONTAL -> coords.x + ship.size > field.size
            Direction.VERTICAL -> coords.y + ship.size > field.size
        }
    }

    private fun hasProhibitedCellsOnPath(coords: Coords, ship: Ship): Boolean {
        for (i in 0 until ship.size) {
            val (x,y) = when (ship.direction) {
                Direction.HORIZONTAL -> coords.x + i to coords.y
                Direction.VERTICAL -> coords.x to coords.y + i
            }

            if (hasAdjacentShip(x, y, ship)) {
                println("Cannot place ship. Prohibited position")
                return true
            }
        }

        return false
    }

    private fun hasAdjacentShip(x: Int, y: Int, shipBeingPlaced: Ship): Boolean {
        val minDx = if (x > 0) -1 else 0
        val maxDx = if (x < field.size - 1) 1 else 0
        val minDy = if (y > 0) -1 else 0
        val maxDy = if (y < field.size - 1) 1 else 0

        for (dx in minDx..maxDx) {
            for (dy in minDy until maxDy) {
                // Ignore cell for which check is being performed
                if (dx != 0 && dy != 0) {
                    val adjacentCell = field[y + dy][x + dx]
                    // Ignore if adjacent cells has shipBeingPlaced
                    if (adjacentCell != FieldConstants.EMPTY && adjacentCell != shipBeingPlaced.hashCode()) {
                        return true
                    }
                }
            }
        }
        return false
    }


    private fun placeShipInternal(coords: Coords, ship: Ship) {
        for (i in 0 until ship.size) {
            val (x,y) = when (ship.direction) {
                Direction.HORIZONTAL -> coords.x + i to coords.y
                Direction.VERTICAL -> coords.x to coords.y + i
            }

            field[y][x] = ship.hashCode()
        }
    }
}

fun Board.placeShipsForDemo() {
    placeShip(Coords(0, 0), SmallShip(direction = Direction.HORIZONTAL))
    placeShip(Coords(0, 2), SmallShip(direction = Direction.HORIZONTAL))
    placeShip(Coords(0, 4), SmallShip(direction = Direction.VERTICAL))
    placeShip(Coords(0, 6), SmallShip(direction = Direction.HORIZONTAL))
    placeShip(Coords(3, 0), MediumShip(direction = Direction.VERTICAL))
    placeShip(Coords(5, 0), MediumShip(direction = Direction.HORIZONTAL))
    placeShip(Coords(3, 6), BigShip(direction = Direction.VERTICAL))
    placeShip(Coords(5, 8), BigShip(direction = Direction.HORIZONTAL))
    placeShip(Coords(9, 0), HugeShip(direction = Direction.VERTICAL))
}