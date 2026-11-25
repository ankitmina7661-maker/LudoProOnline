package com.ludopro.multiplayer

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object RoomManager {

    private var socket: Socket? = null

    var onStatus: (String) -> Unit = {}
    var onRoomJoined: (String) -> Unit = {}
    var onDiceResult: (Int) -> Unit = {}

    fun connect(serverUrl: String) {

        try {

            socket?.disconnect()

            socket = IO.socket(serverUrl)

            socket?.on(Socket.EVENT_CONNECT) {
                onStatus("Connected Successfully")
            }

            socket?.on(Socket.EVENT_CONNECT_ERROR) {
                onStatus("Server Connection Failed")
            }

            socket?.on("room_joined") { args ->

                val data = args.firstOrNull() as? JSONObject

                val code = data?.optString("code") ?: ""

                onRoomJoined(code)
                onStatus("Room Joined: $code")
            }

            socket?.on("dice_result") { args ->

                val data = args.firstOrNull() as? JSONObject

                val value = data?.optInt("value") ?: 1

                onDiceResult(value)
            }

            socket?.on("error_message") { args ->
                onStatus(
                    args.firstOrNull()?.toString()
                        ?: "Unknown Error"
                )
            }

            socket?.connect()

        } catch (e: Exception) {

            onStatus("Error: ${e.message}")
        }
    }

    fun joinPublic(name: String) {

        socket?.emit(
            "join_public",
            JSONObject().put("name", name)
        )
    }

    fun createPrivate(name: String) {

        socket?.emit(
            "create_private",
            JSONObject().put("name", name)
        )
    }

    fun joinPrivate(code: String, name: String) {

        socket?.emit(
            "join_private",
            JSONObject()
                .put("code", code)
                .put("name", name)
        )
    }

    fun rollDice() {
        socket?.emit("roll_dice")
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }
}
