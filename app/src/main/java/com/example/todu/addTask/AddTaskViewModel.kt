package com.example.todu.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todu.data.Task
import com.example.todu.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class AddTaskUiState(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val index: Int = 0,
    val isTaskImportant: Boolean = false,
    val isTaskCompleted: Boolean = false,
    val isLoading: Boolean = false
)


@HiltViewModel
class AddTaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private val sdf = SimpleDateFormat("EEEE dd LLL yyyy HH:mm a")
    val currentDateAndTime = sdf.format(Date())

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    fun updateIndex(index: Int) {
        _uiState.update {
            it.copy(index = index)
        }
    }

    fun addTask() {
        if (uiState.value.title.isNotBlank() || uiState.value.description.isNotBlank()) {

            viewModelScope.launch {
                val newTask = Task(
                    id = 0,
                    title = uiState.value.title,
                    description = uiState.value.description,
                    index = uiState.value.index,
                    date = currentDateAndTime,
                    isCompleted = uiState.value.isTaskCompleted,
                    isImportant = uiState.value.isTaskImportant
                )
                repository.insertTask(newTask)
            }
        }
    }
}