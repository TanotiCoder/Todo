package com.example.todu.completed_task

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todu.EmptyScreen
import com.example.todu.R
import com.example.todu.tasks.TasksViewModel
import com.example.todu.tasks.component.TaskColumn
import com.example.todu.utils.Common.Companion.messageNoCompletedTask
import com.example.todu.utils.HomeScreenAppBar


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CompletedTaskScreen(
    //onClickAddTask: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onClickTaskCard: (id: String) -> Unit,
    openDrawer: () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeScreenAppBar(onClickNavigation = { openDrawer() }, R.string.done)
        }
    ) { paddingValues ->
        val uiState by viewModel.tasksUiState.collectAsStateWithLifecycle()

        TaskColumn(
            tasks = uiState.item,
            loading = uiState.isLoading,
            modifier = Modifier.padding(paddingValues),
            onClickImportant = viewModel::updateIsImportant,
            onClickDelete = viewModel::deleteTask,
            onClickDone = viewModel::updateIsCompleted,
            onClickTaskCard = { onClickTaskCard(it.toString()) },
            emptyScreen = { EmptyScreen(label = messageNoCompletedTask) },
        )
    }
}


