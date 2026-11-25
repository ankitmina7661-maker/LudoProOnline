package com.ludopro

data class Player(
    val id: String,
    val name: String,
    val position: Int = -1
)

object GameEngine {

    const val MAX_PLAYERS = 4
    const val BOARD_SIZE = 52

    fun nextTurn(currentTurn: Int): Int {
        return (currentTurn + 1) % MAX_PLAYERS
    }

    fun isValidMove(
        currentPosition: Int,
        dice: Int
    ): Boolean {

        if (dice !in 1..6) return false

        if (currentPosition == -1) {
            return dice == 6
        }

        return currentPosition + dice <= BOARD_SIZE
    }

    fun newPosition(
        currentPosition: Int,
        dice: Int
    ): Int {

        if (!isValidMove(currentPosition, dice)) {
            return currentPosition
        }

        return if (currentPosition == -1) {
            0
        } else {
            currentPosition + dice
        }
    }
}
