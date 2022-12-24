package com.example.todu.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todu.data.Task
import com.example.todu.repository.TaskRepository
import com.example.todu.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TasksUiState(
    val item: List<Task> = emptyList(),
    val isLoading: Boolean = false
)


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val routeName: String? = savedStateHandle["DrawerRoot"]

    val sai = if (routeName == "Bookmark") {
        taskRepository.getImportantTask()
    } else if (routeName == "Done") {
        taskRepository.getCompletedTask()
    } else {
        taskRepository.getTasks()
    }


    private val _taskAsync = sai//taskRepository.getTasks()
        .map { Async.Success(it) }
        .onStart<Async<List<Task>>> { emit(Async.Loading) }

    val tasksUiState: StateFlow<TasksUiState> = combine(_isLoading, _taskAsync) { loading, async ->
        when (async) {
            Async.Loading -> {
                TasksUiState(isLoading = true)
            }
            is Async.Success -> {
                TasksUiState(item = async.data, isLoading = loading)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksUiState(isLoading = true)
    )

    fun updateIsImportant(id: Int, isImportant: Boolean) {
        viewModelScope.launch {
            taskRepository.checkIsImportant(id, isImportant)
        }
    }

    fun updateIsCompleted(id: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.checkIsCompleted(id, isCompleted)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }
}

