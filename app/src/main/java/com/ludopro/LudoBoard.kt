package com.ludopro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun LudoBoard() {

    val red = Color(0xFFE53935)
    val green = Color(0xFF43A047)
    val yellow = Color(0xFFFDD835)
    val blue = Color(0xFF1E88E5)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {

        val cell = size.width / 15f

        for (row in 0 until 15) {
            for (col in 0 until 15) {

                val color = when {
                    row < 6 && col < 6 -> red
                    row < 6 && col > 8 -> green
                    row > 8 && col < 6 -> blue
                    row > 8 && col > 8 -> yellow
                    else -> Color.LightGray
                }

                drawRect(
                    color = color,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        col * cell,
                        row * cell
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        cell - 1f,
                        cell - 1f
                    )
                )
            }
        }

        // Center area
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(
                6 * cell,
                6 * cell
            ),
            size = androidx.compose.ui.geometry.Size(
                3 * cell,
                3 * cell
            )
        )
    }
}
