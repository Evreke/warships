/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.ui

import Action
import Board
import ClientMessage
import ControlPayload
import androidx.compose.runtime.mutableStateOf
import client
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import ru.evreke.ui.style.AppStylesheet

val scope = MainScope()

fun main() {
  renderComposable(rootElementId = "root") {
    Style(AppStylesheet)
    val board = mutableStateOf<Board?>(null)
    val sendChanel = Channel<ClientMessage>()
    var socket: DefaultClientWebSocketSession? = null

    fun initWebSocket() {
      scope.launch {
        client.webSocket(
          method = HttpMethod.Get,
          host = "127.0.0.1",
          port = 8080,
          path = "/ws"
        ) {
          socket = this
          while (true) {
            console.log("Websocket")
            sendChanel.consumeEach {
              console.log("Sending message $it")
              sendSerialized(it)
            }
          }
        }
      }

//      scope.launch {
//        while (true) {
//          console.log("Websocket incoming")
//          val serverMessage = socket.receiveDeserialized<ServerMessage>()
//          when (serverMessage.payload) {
//            is ControlPayload -> {
//              when (serverMessage.payload.action) {
//                Action.INIT -> console.log(serverMessage.payload.action)
//                Action.JOIN -> console.log(serverMessage.payload.action)
//              }
//            }
//
//            is DataPayload -> board.value = serverMessage.payload.board
//          }
//        }
//      }
    }

    initWebSocket()

    H1 {
      Text("Warships")
    }

    Board(board)
    CustomButton("Init game") {
      scope.launch {
        sendChanel.send(ClientMessage(ControlPayload(Action.INIT)))
      }
    }
  }
}