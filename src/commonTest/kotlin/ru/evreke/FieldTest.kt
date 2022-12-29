/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke

internal class FieldTest {

  private fun initField(fieldSize: Int = 10): Field {
    val list = mutableListOf<MutableList<Int?>>()
    for (i in 0 until fieldSize) {
      list.add(MutableList(fieldSize) { null })
    }
    return Field(fieldSize, list)
  }
}