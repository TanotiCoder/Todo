package com.example.todu.todu_drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todu.R
import com.example.todu.utils.DrawerContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerTodu(
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit,
    navigationOnClick: (route: String) -> Unit,
    drawerButtonData: MutableList<DrawerContent>,
    currentRoot: String
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.padding(end = 56.dp)) {
                DrawerHeader(modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp))
                Spacer(modifier = Modifier.height(12.dp))
                drawerButtonData.forEachIndexed { index, drawerContent ->
                    NavigationDrawerItem(
                        label = { Text(text = drawerContent.route) },
                        selected = drawerContent.label == currentRoot,
                        icon = {
                            Icon(
                                imageVector = drawerContent.icon,
                                contentDescription = drawerContent.route
                            )
                        },
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navigationOnClick(drawerContent.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            selectedIconColor = Color.Black,
                            selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            unselectedIconColor = Color.DarkGray,
                            unselectedTextColor = Color.DarkGray,
                        ),
                    )
                }
            }
        },
        content = { content() },
    )
}


@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painterResource(R.drawable.logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Light
        )
    }
}

