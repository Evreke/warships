package ru.evreke

import ActualPlayer
import ClientMessage
import ControlPayload
import DataPayload
import Game
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.reflect.*
import io.ktor.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@OptIn(ExperimentalCoroutinesApi::class)
fun Application.sockets() {
  install(WebSockets) {
    contentConverter = KotlinxWebsocketSerializationConverter(Json)
  }

  val playerSessions = ConcurrentHashMap<String, Game>()

  routing {
    webSocket("/ws") {
      val playerUUID = UUID.randomUUID().toString()
      if (!playerSessions.contains(playerUUID)) {
        playerSessions[playerUUID] = Game(ActualPlayer(9)).also { it.sessions.add(this) }
      }
      println("Player $playerUUID started session")

      try {
        while (true) {
            val message = receiveDeserialized<ClientMessage>()

            when (message.payload) {
              is ControlPayload -> println(message)
              is DataPayload -> println(message)
            }
          }
      } catch (e: Exception) {
        println("Error $e")
      } finally {
        playerSessions.remove(playerUUID)
        this.close()
      }
    }
  }
}