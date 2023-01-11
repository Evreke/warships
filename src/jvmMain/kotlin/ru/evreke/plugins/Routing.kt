/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import ru.evreke.Game
import ru.evreke.initShips

fun Application.configureRouting() {

  // Starting point for a Ktor app:
  routing {
    get("/") {
      call.respondText(
        this::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
      )
    }
    webSocket("/echo") {
      send("echo from WS")
    }
    webSocket("/ws") {
      val game = Game().also { it.field.initShips() }
      sendSerialized(game.field)
    }
  }
  routing {
    static("/") {
      resources("")
    }
  }
}
