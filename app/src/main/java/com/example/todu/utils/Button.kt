package com.example.todu.utils

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.todu.ui.theme.liteOrange
import com.example.todu.ui.theme.orange

@Composable
fun FloatingButton(icon: ImageVector, onClickFloating: () -> Unit,contentDescription:String) {
    FloatingActionButton(
        onClick = onClickFloating,
        backgroundColor = liteOrange,
        contentColor = orange,
        modifier = Modifier.size(56.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            Modifier.size(24.dp)
        )
    }
}