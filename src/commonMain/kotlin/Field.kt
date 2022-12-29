/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.random.Random

enum class Edges {
  TOP,
  RIGHT,
  BOTTOM,
  LEFT,
}

data class Coords(
  val x: Int,
  val y: Int,
)

@Serializable
data class Field(
  val size: Int,
  val field: MutableList<MutableList<Int?>>,
) {

  fun placeShip(coords: Coords, ship: Ship, resolvePosition: () -> Position): Boolean {
    val shipSize = ship.size

    // is there another ship or prohibited cells in path?
    // TODO MOVE OUTSIDE
    when (ship.direction) {
      Direction.HORIZONTAL -> for (i in 0 until shipSize) {
        if (field[coords.y][coords.x + i] == 1 || field[coords.y][coords.x + i] != null) {
          println("cannot place ship. Prohibited position")
          return false
        }
      }
      Direction.VERTICAL -> for (i in 0 until shipSize) {
        if (field[coords.y + i][coords.x] == 1 || field[coords.y + i][coords.x] != null) {
          println("cannot place ship. Prohibited position")
          return false
        }
      }
    }

    return when (resolvePosition()) {
      Position.TOP_LEFT -> placeInTopLeftCorner(ship, coords)
      Position.TOP_RIGHT -> placeInTopRightCorner(ship, coords)
      Position.BOTTOM_LEFT -> placeInBottomLeftCorner(ship, coords)
      Position.BOTTOM_RIGHT -> placeInBottomRightCorner(ship, coords)
      Position.EDGE -> placeOnEdge(ship, coords)
      Position.FREE_POSITION -> place(ship, coords)
    }
  }

  private fun place(ship: Ship, coords: Coords): Boolean {
    when (ship.direction) {
      Direction.HORIZONTAL -> {
        if (coords.x > 0) {
          field[coords.y - 1][coords.x - 1] = 1
          field[coords.y][coords.x - 1] = 1
          field[coords.y + 1][coords.x - 1] = 1
        }

        for (i in 0 until ship.size) {
          field[coords.y][coords.x + i] = ship.hashCode()
          field[coords.y - 1][coords.x + i] = 1
          field[coords.y + 1][coords.x + i] = 1
        }

        if (coords.x + ship.size < size) {
          field[coords.y - 1][coords.x + ship.size] = 1
          field[coords.y][coords.x + ship.size] = 1
          field[coords.y + 1][coords.x + ship.size] = 1
        }

        return true
      }
      Direction.VERTICAL -> {
        if (coords.y > 0) {
          field[coords.y - 1][coords.x - 1] = 1
          field[coords.y - 1][coords.x] = 1
          field[coords.y - 1][coords.x + 1] = 1
        }

        for (i in 0 until ship.size) {
          field[coords.y + i][coords.x] = ship.hashCode()
          field[coords.y + i][coords.x - 1] = 1
          field[coords.y + i][coords.x + 1] = 1
        }

        if (coords.y + ship.size < size) {
          field[coords.y + ship.size][coords.x - 1] = 1
          field[coords.y + ship.size][coords.x] = 1
          field[coords.y + ship.size][coords.x + 1] = 1
        }

        return true
      }
    }
  }

  private fun placeOnEdge(ship: Ship, coords: Coords): Boolean {
    return when (detectEdge(coords, size - 1)) {
      Edges.TOP -> placeOnTop(ship, coords)
      Edges.RIGHT -> placeOnRight(ship, coords)
      Edges.BOTTOM -> placeOnBottom(ship, coords)
      Edges.LEFT -> placeOnLeft(ship, coords)
    }
  }

  private fun detectEdge(coords: Coords, fieldSize: Int): Edges = when {
    coords.x == 0 -> Edges.LEFT
    coords.x == fieldSize -> Edges.RIGHT
    coords.y == 0 -> Edges.TOP
    coords.y == fieldSize -> Edges.BOTTOM
    else -> throw IllegalStateException("Inappropriate method call")
  }

  private fun placeOnLeft(ship: Ship, coords: Coords): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][coords.x + i] = ship.hashCode()
        field[coords.y - 1][coords.x + i] = 1
        field[coords.y + 1][coords.x + i] = 1
      }
      field[coords.y - 1][ship.size - 1 + coords.x + 1] = 1
      field[coords.y][ship.size - 1 + coords.x + 1] = 1
      field[coords.y + 1][ship.size - 1 + coords.x + 1] = 1
    } else {
      // above ship cells marked as restricted
      if (coords.y - ship.size > 0) {
        field[coords.y - 1][coords.x] = 1
        field[coords.y - 1][coords.x + 1] = 1
      }

      for (i in 0 until ship.size) {
        field[coords.y + i][coords.x] = ship.hashCode()
        field[coords.y + i][coords.x + 1] = 1
      }

      // underneath ship cells marked as restricted
      if (coords.y + ship.size < size) {
        field[coords.y + ship.size][coords.x + 1] = 1
        field[coords.y + ship.size][coords.x] = 1
      }
    }
    return true
  }

  private fun placeOnBottom(ship: Ship, coords: Coords): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      if (coords.x > 0) {
        field[coords.y][coords.x - 1] = 1
        field[coords.y - 1][coords.x - 1] = 1
      }

      for (i in 0 until ship.size) {
        field[coords.y][coords.x + i] = ship.hashCode()
        field[coords.y - 1][coords.x + i] = 1
      }

      if (coords.x + ship.size < size) {
        field[coords.y - 1][coords.x + ship.size] = 1
        field[coords.y][coords.x + ship.size] = 1
      }
    } else {
      for (i in 0 until ship.size) {
        field[coords.y][coords.x + i] = ship.hashCode()
        field[coords.y - 1][coords.x + i] = 1
      }
      field[coords.y - 1][coords.x - 1] = 1
      field[coords.y][coords.x - 1] = 1

      field[coords.y - 1][coords.x + 1] = 1
      field[coords.y][coords.x + 1] = 1
    }
    return true
  }

  private fun placeOnRight(ship: Ship, coords: Coords): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][size - 1 - i] = ship.hashCode()
        field[coords.y - 1][size - 1 - i] = 1
        field[coords.y + 1][size - 1 - i] = 1
      }
      field[coords.y - 1][size - 1 - ship.size] = 1
      field[coords.y][size - 1 - ship.size] = 1
      field[coords.y + 1][size - 1 - ship.size] = 1
    } else {
      field[coords.y - 1][coords.x] = 1
      field[coords.y - 1][coords.x - 1] = 1

      for (i in 0 until ship.size) {
        field[coords.y + i][coords.x] = ship.hashCode()
        field[coords.y + i][coords.x - 1] = 1
      }

      field[coords.y + ship.size][coords.x - 1] = 1
      field[coords.y + ship.size][coords.x] = 1
    }
    return true
  }

  private fun placeOnTop(ship: Ship, coords: Coords): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      if (coords.x - ship.size > 0) {
        field[coords.y][coords.x - 1] = 1
        field[coords.y + 1][coords.x - 1] = 1
      }

      for (i in 0 until ship.size) {
        field[coords.y][i + coords.x] = ship.hashCode()
        field[coords.y + 1][i + coords.x] = 1
      }

      if (coords.x + ship.size < size) {
        field[coords.y][coords.x + 1] = 1
        field[coords.y + 1][coords.x + 1] = 1
      }

    } else {
      for (i in 0 until ship.size) {
        field[i + coords.y][coords.x] = ship.hashCode()
        field[i + coords.y][coords.x - 1] = 1
        field[i + coords.y][coords.x + 1] = 1
      }

      field[coords.y + ship.size][coords.x - 1] = 1
      field[coords.y + ship.size][coords.x] = 1
      field[coords.y + ship.size][coords.x + 1] = 1
    }
    return true
  }

  private fun placeInBottomRightCorner(
    ship: Ship,
    coords: Coords,
  ): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][size - 1 - i] = ship.hashCode()
        field[coords.y - 1][coords.x] = 1
      }
      field[coords.y - 1][coords.x - 1] = 1
      field[coords.y][coords.x - 1] = 1
      return true
    } else {
      for (i in 0 until ship.size) {
        field[size - 1 - i][coords.x] = ship.hashCode()
        field[coords.y][coords.x - 1] = 1
      }
      field[coords.y - 1][coords.x - 1] = 1
      field[coords.y - 1][coords.x] = 1
      return true
    }
  }

  private fun placeInBottomLeftCorner(
    ship: Ship,
    coords: Coords,
  ): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][i] = ship.hashCode()
        field[coords.y - 1][i] = 1
      }
      field[coords.y - 1][coords.x + ship.size] = 1
      field[coords.y][coords.x + ship.size] = 1
      return true
    } else {
      for (i in 0 until ship.size) {
        field[size - 1 - i][coords.x] = ship.hashCode()
        field[coords.y][coords.x + 1] = 1
      }
      field[coords.y - 1][coords.x + 1] = 1
      field[coords.y - 1][coords.x] = 1
      return true
    }
  }

  private fun placeInTopRightCorner(
    ship: Ship,
    coords: Coords,
  ): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][size - 1 - i] = ship.hashCode()
        field[coords.y + 1][coords.x] = 1
      }
      field[coords.y + 1][coords.x - 1] = 1
      field[coords.y][coords.x - 1] = 1
      return true
    } else {
      for (i in 0 until ship.size) {
        field[i][coords.x] = ship.hashCode()
        field[i][coords.x - 1] = 1
      }
      field[coords.y + ship.size][coords.x - 1] = 1
      field[coords.y + ship.size][coords.x] = 1
      return true
    }
  }

  private fun placeInTopLeftCorner(
    ship: Ship,
    coords: Coords,
  ): Boolean {
    if (ship.direction == Direction.HORIZONTAL) {
      for (i in 0 until ship.size) {
        field[coords.y][i] = ship.hashCode()
        field[coords.y + 1][i] = 1
      }
      field[coords.y][ship.size] = 1
      field[coords.y + 1][ship.size] = 1
      return true
    } else {
      for (i in 0 until ship.size) {
        field[i][coords.x] = ship.hashCode()
        field[i][coords.x + 1] = 1
      }
      field[coords.y + ship.size][coords.x] = 1
      field[coords.y + ship.size][coords.x + 1] = 1
      return true
    }
  }
}