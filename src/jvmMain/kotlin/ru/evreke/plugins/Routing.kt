/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.plugins

import Action
import ActualPlayer
import ClientMessage
import ControlPayload
import DataPayload
import Game
import ServerMessage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.evreke.sockets

fun Application.configureRouting() {

  // Starting point for a Ktor app:
  routing {
    get("/") {
      call.respondText(
        this::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
      )
    }
//    route("/ws") {
//      webSocket("/ws") {
//        while (true) {
//          val message = receiveDeserialized<ClientMessage>()
//          println("Message received $message")
//          when (message.payload) {
//            is ControlPayload -> {
//              when (message.payload.action) {
//                Action.INIT -> sendSerialized(
//                  ServerMessage(
//                    DataPayload(
//                      Game(ActualPlayer(9)).board
//                    )
//                  )
//                )
//
//                Action.JOIN -> TODO()
//              }
//            }
//
//            is DataPayload -> TODO()
//          }
//        }
//      }
//    }
  }

  sockets()

  routing {
    static("/") {
      resources("")
    }
  }
}
