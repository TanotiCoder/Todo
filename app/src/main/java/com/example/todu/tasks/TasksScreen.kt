package com.example.todu.tasks

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todu.EmptyScreen
import com.example.todu.R
import com.example.todu.tasks.component.TaskColumn
import com.example.todu.utils.Common.Companion.message
import com.example.todu.utils.FloatingButton
import com.example.todu.utils.HomeScreenAppBar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TasksScreen(
    onClickAddTask: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onClickTaskCard: (id: String) -> Unit,
    openDrawer: () -> Unit,
) {
    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        FloatingButton(
            icon = Icons.Default.Add,
            onClickFloating = { onClickAddTask() },
            contentDescription = "Add",
        )
    },
        topBar = {
            HomeScreenAppBar(onClickNavigation = { openDrawer() }, R.string.app_name)
        }
    ) { paddingValues ->
        val uiState by viewModel.tasksUiState.collectAsStateWithLifecycle()
        TaskColumn(
            tasks = uiState.item,
            loading = uiState.isLoading,
            modifier = Modifier.padding(paddingValues),
            onClickDone = viewModel::updateIsCompleted,
            onClickImportant = viewModel::updateIsImportant,
            onClickDelete = viewModel::deleteTask,
            onClickTaskCard = { onClickTaskCard(it.toString()) },
            emptyScreen = { EmptyScreen(label = message) }
        )
    }
}