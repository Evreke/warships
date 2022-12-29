/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke

import kotlin.test.Test
import kotlin.test.assertEquals


internal class SmallShipTest {

  private fun initField(fieldSize: Int = 10): Field {
    val list = mutableListOf<MutableList<Int?>>()
    for (i in 0 until fieldSize) {
      list.add(MutableList(fieldSize) { null })
    }
    return Field(fieldSize, list)
  }

  @Test
  fun placeSmallShipInTopLeft() {
    val coords = Coords(0, 0)

    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.TOP_LEFT }
    }

    // Ship placed properly
    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed in top left corner"
    )

    val verticalShipAdjacentCells = listOf(
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
    )
    // Adjacent cells is marked as restricted
    verticalShipAdjacentCells.forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipInTopRight() {
    val coords = Coords(9, 0)

    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.TOP_RIGHT }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed in top right corner"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipInBottomRight() {
    val coords = Coords(9, 9)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.BOTTOM_RIGHT }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed in bottom right corner"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y][coords.x - 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipInBottomLeft() {
    val coords = Coords(0, 9)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.BOTTOM_LEFT }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed in bottom right corner"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipOnLeftEdge() {
    val coords = Coords(0, 4)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.EDGE }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed in bottom right corner"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
      fieldWithVerticalShip.field[coords.y - 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipOnTopEdge() {
    val coords = Coords(4, 0)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.EDGE }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed on top edge"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
      fieldWithVerticalShip.field[coords.y + 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipOnRightEdge() {
    val coords = Coords(9, 4)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.EDGE }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed on right edge"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
      fieldWithVerticalShip.field[coords.y - 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipOnBottomEdge() {
    val coords = Coords(4, 9)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.EDGE }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed on bottom edge"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y][coords.x - 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
      fieldWithVerticalShip.field[coords.y - 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeSmallShipInMiddle() {
    val coords = Coords(4, 4)
    val verticalSmallShip = SmallShip(direction = Direction.VERTICAL)
    val fieldWithVerticalShip = initField(10).apply {
      placeShip(coords, verticalSmallShip) { Position.FREE_POSITION }
    }

    assertEquals(
      verticalSmallShip.hashCode(),
      fieldWithVerticalShip.field[coords.y][coords.x],
      "Single decked vertical ship placed inti inner perimeter"
    )

    listOf(
      fieldWithVerticalShip.field[coords.y - 1][coords.x - 1],
      fieldWithVerticalShip.field[coords.y - 1][coords.x],
      fieldWithVerticalShip.field[coords.y - 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x + 1],
      fieldWithVerticalShip.field[coords.y + 1][coords.x],
      fieldWithVerticalShip.field[coords.y - 1][coords.x - 1],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }
}