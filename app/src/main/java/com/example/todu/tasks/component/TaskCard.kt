package com.example.todu.tasks.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todu.R
import com.example.todu.data.Task
import com.example.todu.ui.theme.green
import com.example.todu.utils.Common.Companion.chipData
import com.example.todu.utils.Common.Companion.message
import com.example.todu.utils.RemoteChipData


@Composable
fun TaskColumn(
    tasks: List<Task>,
    loading: Boolean,
    modifier: Modifier,
    onClickImportant: (id: Int, isImportant: Boolean) -> Unit,
    onClickDelete: (task: Task) -> Unit,
    onClickDone: (id: Int, isCompleted: Boolean) -> Unit,
    onClickTaskCard: (id: Int) -> Unit,
    emptyScreen: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
    ) {

        LazyColumn {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    chipData = chipData[task.index],
                    onClickTaskCard = { onClickTaskCard(task.id) },
                    onClickDelete = { onClickDelete(task) },
                    onClickImportant = { onClickImportant(task.id, !(task.isImportant)) },
                    modifier = modifier,
                    onClickDone = { onClickDone(task.id, !(task.isCompleted)) }
                )
            }
        }

        if (loading) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        if (!loading && tasks.isEmpty()) {
            emptyScreen()
        }
    }
}


@Composable
fun TaskCard(
    task: Task,
    chipData: RemoteChipData,
    onClickTaskCard: () -> Unit,
    onClickDelete: () -> Unit,
    onClickImportant: (id: Int) -> Unit,
    onClickDone: () -> Unit,
    modifier: Modifier
) {
    val bgColor = if (task.isCompleted) green else Color.LightGray
    val tint = if (task.isCompleted) Color.White else Color.Gray
    val icon = if (task.isCompleted) Icons.Filled.DoneAll else Icons.Default.Done

    Card(
        modifier
            .clickable { onClickTaskCard() }
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .widthIn(288.dp, 768.dp)
            .heightIn(160.dp, 182.dp),
        border = BorderStroke(2.dp, Color(chipData.color)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth(0.80f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                TaskCardHeader(
                    chipData = chipData,
                    onClickDelete = { onClickDelete() },
                    onClickImportant = { onClickImportant(task.id) },
                    isImportant = task.isImportant,
                    modifier = modifier
                )
                TaskCardTextContent(title = task.title, description = task.description)
                Text(
                    text = task.date,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp, start = 8.dp),
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                )
            }
            Box(
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onClickDone() }
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Done",
                    tint = tint,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun TaskCardTextContent(title: String, description: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
    }
}

@Composable
fun TaskCardHeader(
    chipData: RemoteChipData,
    onClickDelete: () -> Unit,
    onClickImportant: () -> Unit,
    isImportant: Boolean,
    modifier: Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TaskLabelChips(
            onClickChip = {/*Eat 5 Star, Do Nothing*/ },
            isActive = false,
            chipData = chipData,
            modifier
        )
        TaskCardIconButton(
            color = chipData.color,
            onClickDelete = { onClickDelete() },
            onClickImportant = { onClickImportant() },
            isImportant = isImportant,
            modifier
        )
    }
}

@Composable
fun TaskCardIconButton(
    color: Long, onClickDelete: () -> Unit, onClickImportant: () -> Unit, isImportant: Boolean,
    modifier: Modifier
) {
    Row(
        modifier.border(2.dp, color = Color(color), RoundedCornerShape(bottomStart = 12.dp))
    ) {
        IconButton(onClick = onClickDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }
        Divider(
            modifier
                .width(2.dp)
                .fillMaxHeight(), color = Color(color)
        )
        IconButton(onClick = onClickImportant) {
            Icon(
                imageVector = if (isImportant) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Star",
                tint = Color.Black
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskLabelChips(
    onClickChip: () -> Unit,
    isActive: Boolean,
    chipData: RemoteChipData,
    modifier: Modifier
) {
    val borderStroke = if (isActive) BorderStroke(2.dp, Color.Black)
    else BorderStroke(2.dp, Color(chipData.color))

    Card(
        backgroundColor = Color(chipData.color),
        shape = RoundedCornerShape(50),
        border = borderStroke,
        elevation = 2.dp,
        onClick = { onClickChip() }

    ) {
        Text(
            text = chipData.label,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}
