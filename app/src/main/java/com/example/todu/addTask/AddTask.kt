package com.example.todu.addTask

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todu.utils.EditScreenAppBar
import com.example.todu.R
import com.example.todu.tasks.component.TaskLabelChips
import com.example.todu.utils.Common.Companion.chipData
import com.example.todu.utils.Common.Companion.taskPlaceholder
import com.example.todu.utils.Common.Companion.titlePlaceholder
import com.example.todu.utils.FloatingButton


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AddTask(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: AddTaskViewModel = hiltViewModel(),
    goBack: () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingButton(
                icon = Icons.Default.Done,
                onClickFloating = {
                    viewModel.addTask()
                    goBack()
                },
                contentDescription = "Done"
            )
        },
        topBar = {
            EditScreenAppBar(onClickNavigationBack = {
                viewModel.addTask()
                goBack()
            })
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        paddingValues
        TextContentEditTask(
            indexRoom = uiState.index,
            modifier = Modifier.padding(paddingValues),
            title = uiState.title,
            description = uiState.description,
            date = viewModel.currentDateAndTime,
            OnTitleChange = viewModel::updateTitle,
            OnDescriptionChange = viewModel::updateDescription,
            onChipIndex = viewModel::updateIndex
        )
    }
}


@Composable
fun TextContentEditTask(
    indexRoom: Int,
    modifier: Modifier,
    title: String,
    description: String,
    date: String,
    OnTitleChange: (String) -> Unit,
    OnDescriptionChange: (String) -> Unit,
    onChipIndex: (index: Int) -> Unit
) {


    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
            .verticalScroll(rememberScrollState())
    ) {
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color(chipData[indexRoom].color).copy(alpha = ContentAlpha.high),
        )
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chipData.forEachIndexed { index, remoteChipData ->
                TaskLabelChips(
                    onClickChip = { onChipIndex(index) },
                    isActive = index == indexRoom,
                    chipData = remoteChipData,
                    modifier = modifier
                )
            }
        }
        Text(
            text = date,
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier.padding(horizontal = 16.dp),
            fontStyle = FontStyle.Italic
        )
        OutlinedTextField(
            value = title,
            onValueChange = OnTitleChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = titlePlaceholder,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Gray

                )
            },
            textStyle = MaterialTheme.typography.headlineMedium,
            colors = textFieldColors
        )
        OutlinedTextField(
            value = description, onValueChange = OnDescriptionChange,
            placeholder = {
                Text(
                    text = taskPlaceholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = textFieldColors,

        )
    }
}
