package com.example.todu.task_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todu.utils.DetailScreenAppBar
import com.example.todu.data.Task
import com.example.todu.tasks.component.TaskLabelChips
import com.example.todu.utils.Common.Companion.chipData


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TaskDetail(
    goBackOnClick: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.taskFlowUiState.collectAsStateWithLifecycle()

    Scaffold(Modifier.fillMaxSize(), scaffoldState, topBar = {
        DetailScreenAppBar( onClickNavigationBack = { goBackOnClick() })
    }) { paddingValues ->
        uiState.value.itemFlow?.let {
            TaskDetailContent(
                task = it,
                modifier = Modifier.padding(paddingValues),
            )
        }
        if (uiState.value.isLoading) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun TaskDetailContent(task: Task, modifier: Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TaskLabelChips(
                onClickChip = { },
                isActive = false,
                chipData = chipData[task.index],
                modifier = modifier
            )

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {}
            )
        }
        Text(
            text = task.date,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp),
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )
        Text(
            text = task.title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}