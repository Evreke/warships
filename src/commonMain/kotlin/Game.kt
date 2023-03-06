import io.ktor.websocket.*

/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */


class Game(private var currentPlayer: ActualPlayer) {
    val board: Board
    private val players = linkedSetOf<ActualPlayer>()
    val sessions = mutableListOf<WebSocketSession>()
    private val isStarted: Boolean = false
    private var move: Int = 0
    private val isWithBot: Boolean = true

    init {
        board = initField()
        if (isWithBot) {
            board.placeShipsForDemo()
        }
    }

    companion object {
        private const val fieldSize = 10
    }

    fun incrementMove() {
        move++
    }

    fun isGameOver() {
        if (currentPlayer.shipsAmount == 0.toByte()) {
            // TODO("Send game over signal")
        }
        // TODO("Handle game over")
    }

    fun switchPlayers() {
        currentPlayer = players.indexOf(currentPlayer).let {
            if (players.size == it) {
                players.first()
            } else {
                players.elementAt(it + 1)
            }
        }
    }

    fun handleShot(coords: Coords) {
        if (isShotPossible(coords)) {
            board.field[coords.y][coords.x] = FieldConstants.WRECKED
        } else {
            TODO("Handle wrong move")
        }
    }

    fun isShotPossible(coords: Coords): Boolean = board.field[coords.y][coords.x]
        .let { it != FieldConstants.PROHIBITED || it != FieldConstants.EMPTY }

    private fun initField(): Board {
        val list = mutableListOf<MutableList<Int>>()
        for (i in 0 until fieldSize) {
            list.add(MutableList(fieldSize) { FieldConstants.EMPTY })
        }
        return Board(fieldSize, list)
    }
}