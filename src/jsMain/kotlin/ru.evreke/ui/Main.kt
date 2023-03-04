/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.ui

import Board
import FieldConstants
import androidx.compose.runtime.mutableStateOf
import client
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import ru.evreke.ui.style.AppStylesheet

val scope = MainScope()

fun main() {
  renderComposable(rootElementId = "root") {
    Style(AppStylesheet)
    val board = mutableStateOf<Board?>(null)

    H1 {
      Text("Warships")
    }

    scope.launch {
      client.webSocket(
        method = HttpMethod.Get,
        host = "127.0.0.1",
        port = 8080,
        path = "/ws"
      ) {
        board.value = receiveDeserialized()
        console.log(board.value)
      }
    }


    Div(
      attrs = { classes(AppStylesheet.fieldContainer) }
    ) {
      board.value?.field?.mapIndexed { y, row ->
        row.mapIndexed { x, cell ->
          Div({
            classes(AppStylesheet.fieldCell)
            attr("data-x", x.toString())
            attr("data-y", y.toString())
          }) {
            cell.let {
              if (it != FieldConstants.EMPTY) {
                Text(cell.toString())
              }
            }
          }
        }
      }
    }
  }
}