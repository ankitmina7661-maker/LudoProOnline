package com.ludopro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import com.ludopro.multiplayer.RoomManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                var playerName by remember {
                    mutableStateOf("Player")
                }

                var roomCode by remember {
                    mutableStateOf("")
                }

                var status by remember {
                    mutableStateOf("Welcome to Ludo Pro")
                }

                var diceValue by remember {
                    mutableIntStateOf(1)
                }

                var inGame by remember {
                    mutableStateOf(false)
                }

                RoomManager.onStatus = {
                    status = it
                }

                RoomManager.onRoomJoined = {
                    roomCode = it
                    inGame = true
                }

                RoomManager.onDiceResult = {
                    diceValue = it
                }

                if (!inGame) {
                    HomeScreen(
                        playerName = playerName,
                        roomCode = roomCode,
                        status = status,
                        onNameChange = { playerName = it },
                        onCodeChange = { roomCode = it },
                        onConnect = {
                            RoomManager.connect(
                                "https://YOUR-SERVER.onrender.com"
                            )
                        },
                        onPublic = {
                            RoomManager.joinPublic(playerName)
                        },
                        onCreate = {
                            RoomManager.createPrivate(playerName)
                        },
                        onJoin = {
                            RoomManager.joinPrivate(
                                roomCode,
                                playerName
                            )
                        }
                    )
                } else {
                    GameScreen(
                        roomCode = roomCode,
                        diceValue = diceValue,
                        status = status,
                        onRoll = {
                            RoomManager.rollDice()
                        }
                    )
                }
            }
        }
    }
}
