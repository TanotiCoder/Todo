package com.example.todu.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todu.R
import com.example.todu.ui.theme.liteOrange


@Composable
fun HomeScreenAppBar(onClickNavigation: () -> Unit, title: Int) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title), fontWeight = FontWeight.Light)
        },
        navigationIcon = {
            IconButton(
                onClick = { onClickNavigation() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Menu",
                    Modifier.size(32.dp)
                )
            }
        },
        backgroundColor = liteOrange,
        contentColor = Color.Black
    )
}

@Composable
fun EditScreenAppBar(onClickNavigationBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.add_task), fontWeight = FontWeight.Light)
        },
        navigationIcon = {
            IconButton(
                onClick = { onClickNavigationBack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Cancel")
            }
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
    )
}

@Composable
fun DetailScreenAppBar(
    onClickNavigationBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.detail_task), fontWeight = FontWeight.Light)
        },
        navigationIcon = {
            IconButton(
                onClick = { onClickNavigationBack() }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        backgroundColor = liteOrange,
        contentColor = Color.Black
    )
}