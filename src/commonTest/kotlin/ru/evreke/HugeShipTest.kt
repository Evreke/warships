/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class HugeShipTest {

  @Test
  fun placeVerticalHugeShipInTopLeft() {
    val coords = Coords(0, 0)

    val ship = HugeShip(direction = Direction.VERTICAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 2][coords.x],
      field.field[coords.y + 3][coords.x],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked vertical ship placed in top left corner")
    }

    listOf(
      field.field[coords.y][coords.x + 1],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 2][coords.x + 1],
      field.field[coords.y + 3][coords.x + 1],
      field.field[coords.y + 4][coords.x + 1],
      field.field[coords.y + 4][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipInTopLeft() {
    val coords = Coords(0, 0)

    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed in top left corner")
    }

    listOf(
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 1][coords.x + 2],
      field.field[coords.y + 1][coords.x + 3],
      field.field[coords.y + 1][coords.x + 4],
      field.field[coords.y][coords.x + 4],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }

  }

  @Test
  fun placeVerticalHugeShipInTopRight() {
    val coords = Coords(9, 0)

    val ship = HugeShip(direction = Direction.VERTICAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 2][coords.x],
      field.field[coords.y + 3][coords.x],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked vertical ship placed in top right corner")
    }

    listOf(
      field.field[coords.y][coords.x - 1],
      field.field[coords.y + 1][coords.x - 1],
      field.field[coords.y + 2][coords.x - 1],
      field.field[coords.y + 3][coords.x - 1],
      field.field[coords.y + 4][coords.x - 1],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipInTopRightIsNotAllowed() {
    val coords = Coords(9, 0)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }


  @Test
  fun placeVerticalHugeShipInBottomRightIsNotAllowed() {
    val coords = Coords(9, 9)

    val ship = HugeShip(direction = Direction.VERTICAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }


  @Test
  fun placeHorizontalHugeShipInBottomRightIsNotAllowed() {
    val coords = Coords(9, 9)

    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }

  @Test
  fun placeVerticalHugeShipInBottomLeftIsNotAllowed() {
    val coords = Coords(0, 9)

    val ship = HugeShip(direction = Direction.VERTICAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }

  @Test
  fun placeHorizontalHugeShipInBottomLeft() {
    val coords = Coords(0, 9)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Single decked horizontal ship placed in bottom left corner")
    }


    listOf(
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x + 1],
      field.field[coords.y - 1][coords.x + 2],
      field.field[coords.y - 1][coords.x + 3],
      field.field[coords.y - 1][coords.x + 4],
      field.field[coords.y][coords.x + 4],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeVerticalHugeShipOnLeftEdge() {
    val coords = Coords(0, 6)

    val ship = HugeShip(direction = Direction.VERTICAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 2][coords.x],
      field.field[coords.y + 3][coords.x],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed in bottom left corner")
    }

    listOf(
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x + 1],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 2][coords.x + 1],
      field.field[coords.y + 3][coords.x + 1],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipOnLeftEdge() {
    val coords = Coords(0, 6)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed on left edge")
    }

    listOf(
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x + 1],
      field.field[coords.y - 1][coords.x + 2],
      field.field[coords.y - 1][coords.x + 3],
      field.field[coords.y - 1][coords.x + 4],
      field.field[coords.y][coords.x + 4],
      field.field[coords.y + 1][coords.x + 4],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 1][coords.x + 2],
      field.field[coords.y + 1][coords.x + 3],
      field.field[coords.y + 1][coords.x + 4],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeVerticalHugeShipOnTopEdge() {
    val coords = Coords(6, 0)
    val ship = HugeShip(direction = Direction.VERTICAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field


    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 2][coords.x],
      field.field[coords.y + 3][coords.x],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked vertical ship placed on top edge")
    }


    listOf(
      field.field[coords.y][coords.x - 1],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y + 1][coords.x - 1],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 2][coords.x - 1],
      field.field[coords.y + 2][coords.x + 1],
      field.field[coords.y + 3][coords.x - 1],
      field.field[coords.y + 3][coords.x + 1],
      field.field[coords.y + 4][coords.x - 1],
      field.field[coords.y + 4][coords.x + 1],
      field.field[coords.y + 4][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipOnTopEdge() {
    val coords = Coords(6, 0)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    // Ship placed properly
    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed on top edge")
    }

    listOf(
      field.field[coords.y][coords.x - 1],
      field.field[coords.y + 1][coords.x - 1],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 1][coords.x + 2],
      field.field[coords.y + 1][coords.x + 3],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeVerticalHugeShipOnRightEdge() {
    val coords = Coords(9, 4)
    val ship = HugeShip(direction = Direction.VERTICAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field


    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 2][coords.x],
      field.field[coords.y + 3][coords.x],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked vertical ship placed on right edge")
    }


    listOf(
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x - 1],
      field.field[coords.y][coords.x - 1],
      field.field[coords.y + 1][coords.x - 1],
      field.field[coords.y + 2][coords.x - 1],
      field.field[coords.y + 3][coords.x - 1],
      field.field[coords.y + 4][coords.x - 1],
      field.field[coords.y + 4][coords.x],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipOnRightEdgeIsNotAllowed() {
    val coords = Coords(9, 4)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }

  @Test
  fun placeVerticalHugeShipOnBottomEdgeIsNotAllowed() {
    val coords = Coords(4, 9)
    val ship = HugeShip(direction = Direction.VERTICAL)
    val game = Game()
    game.apply {
      assertFalse("Ship couldn't be placed out of field bounds") {
        placeShip(coords, ship)
      }
    }
  }

  @Test
  fun placeHorizontalHugeShipOnBottomEdge() {
    val coords = Coords(4, 9)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed on bottom edge")
    }

    listOf(
      field.field[coords.y][coords.x - 1],
      field.field[coords.y - 1][coords.x - 1],
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x + 1],
      field.field[coords.y - 1][coords.x + 2],
      field.field[coords.y - 1][coords.x + 3],
      field.field[coords.y - 1][coords.x + 4],
      field.field[coords.y][coords.x + 4],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }

  @Test
  fun placeHorizontalHugeShipInMiddle() {
    val coords = Coords(4, 4)
    val ship = HugeShip(direction = Direction.HORIZONTAL)
    val field = Game().apply {
      placeShip(coords, ship)
    }.field

    listOf(
      field.field[coords.y][coords.x],
      field.field[coords.y][coords.x + 1],
      field.field[coords.y][coords.x + 2],
      field.field[coords.y][coords.x + 3],
    ).forEach {
      assertEquals(ship.hashCode(), it, "Four decked horizontal ship placed in middle")
    }

    listOf(
      field.field[coords.y - 1][coords.x - 1],
      field.field[coords.y - 1][coords.x],
      field.field[coords.y - 1][coords.x + 1],
      field.field[coords.y - 1][coords.x + 2],
      field.field[coords.y - 1][coords.x + 3],
      field.field[coords.y - 1][coords.x + 4],
      field.field[coords.y][coords.x + 4],
      field.field[coords.y + 1][coords.x + 4],
      field.field[coords.y + 1][coords.x],
      field.field[coords.y + 1][coords.x + 1],
      field.field[coords.y + 1][coords.x + 2],
      field.field[coords.y + 1][coords.x + 3],
      field.field[coords.y + 1][coords.x + 4],
    ).forEach {
      assertEquals(1, it, "Adjacent cells marked as restricted")
    }
  }
}
