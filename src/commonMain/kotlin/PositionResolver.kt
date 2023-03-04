/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

class PositionResolver {

  fun resolvePosition(coords: Coords, fieldSize: Int): Position = when {
    isTopLeftCorner(coords) -> Position.TOP_LEFT
    isTopRightCorner(coords, fieldSize) -> Position.TOP_RIGHT
    isBottomRightCorner(coords, fieldSize) -> Position.BOTTOM_RIGHT
    isBottomLeftCorner(coords, fieldSize) -> Position.BOTTOM_LEFT
    isEdge(coords, fieldSize) -> Position.EDGE
    else -> Position.FREE_POSITION
  }

  private fun isTopLeftCorner(coords: Coords): Boolean = coords.x == 0 && coords.y == 0

  private fun isTopRightCorner(coords: Coords, fieldSize: Int): Boolean = coords.x == fieldSize && coords.y == 0

  private fun isBottomRightCorner(coords: Coords, fieldSize: Int): Boolean = coords.x == fieldSize && coords.y == fieldSize

  private fun isBottomLeftCorner(coords: Coords, fieldSize: Int): Boolean = coords.x == 0 && coords.y == fieldSize

  private fun isEdge(coords: Coords, fieldSize: Int): Boolean = coords.x == 0
          || coords.x == fieldSize
          || coords.y == 0
          || coords.y == fieldSize
}

enum class Position {
  TOP_LEFT,
  TOP_RIGHT,
  BOTTOM_LEFT,
  BOTTOM_RIGHT,
  EDGE,
  FREE_POSITION,
}