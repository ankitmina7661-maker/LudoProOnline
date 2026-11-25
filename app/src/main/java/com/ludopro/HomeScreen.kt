package com.ludopro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    playerName: String,
    roomCode: String,
    status: String,
    onNameChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onConnect: () -> Unit,
    onPublic: () -> Unit,
    onCreate: () -> Unit,
    onJoin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "LUDO PRO ONLINE",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = playerName,
            onValueChange = onNameChange,
            label = { Text("Player Name") }
        )

        Spacer(Modifier.height(15.dp))

        Button(
            onClick = onConnect,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CONNECT SERVER")
        }

        Button(
            onClick = onPublic,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("PLAY PUBLIC")
        }

        Spacer(Modifier.height(15.dp))

        OutlinedTextField(
            value = roomCode,
            onValueChange = onCodeChange,
            label = { Text("Room Code") }
        )

        Button(
            onClick = onJoin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("JOIN ROOM")
        }

        OutlinedButton(
            onClick = onCreate,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CREATE PRIVATE ROOM")
        }

        Spacer(Modifier.height(20.dp))

        Text(status)
    }
}
