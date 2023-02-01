/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.plugins

import ActualPlayer
import Game
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.configureRouting() {

  // Starting point for a Ktor app:
  routing {
    get("/") {
      call.respondText(
        this::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
      )
    }
    webSocket("/ws") {
      sendSerialized(Game(ActualPlayer(9)).board)
    }
  }
  routing {
    static("/") {
      resources("")
    }
  }
}
