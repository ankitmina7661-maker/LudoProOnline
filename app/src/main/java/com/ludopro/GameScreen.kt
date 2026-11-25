package com.ludopro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(
    roomCode: String,
    diceValue: Int,
    status: String,
    onRoll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "LUDO PRO",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Room: $roomCode")

        Spacer(Modifier.height(20.dp))

        LudoBoard()

        Spacer(Modifier.height(20.dp))

        DiceView(
            value = diceValue,
            onRoll = onRoll
        )

        Spacer(Modifier.height(15.dp))

        Text(status)
    }
}
