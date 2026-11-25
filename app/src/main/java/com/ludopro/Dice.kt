package com.ludopro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiceView(
    value: Int,
    onRoll: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🎲 $value",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = onRoll
        ) {
            Text("ROLL DICE")
        }
    }
}
