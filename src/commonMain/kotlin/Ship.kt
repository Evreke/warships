/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

import kotlinx.serialization.Serializable

@Serializable
sealed class Ship(
  val size: Int,
  val direction: Direction
)

class SmallShip(
  size: Int = 1,
  direction: Direction
) : Ship(size, direction)

class MediumShip(
  size: Int = 2,
  direction: Direction
) : Ship(size, direction)

class BigShip(
  size: Int = 3,
  direction: Direction
) : Ship(size, direction)

class HugeShip(
  size: Int = 4,
  direction: Direction
) : Ship(size, direction)

enum class Direction {
  HORIZONTAL,
  VERTICAL
}
