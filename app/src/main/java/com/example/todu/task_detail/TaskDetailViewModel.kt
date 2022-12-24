package com.example.todu.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todu.data.Task
import com.example.todu.repository.TaskRepository
import com.example.todu.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class TaskFlowUiState(
    val itemFlow: Task? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id2: String? = savedStateHandle["id"]
    val id = id2?.toInt()

    private val _isLoading = MutableStateFlow(false)
    private val _taskFlowAsync = repository.getTaskFlow(id!!)
        .map { Async.Success(it) }
        .onStart<Async<Task>> { emit(Async.Loading) }

    val taskFlowUiState: StateFlow<TaskFlowUiState> =
        combine(_isLoading, _taskFlowAsync) { loading, asyncFlow ->
            when (asyncFlow) {
                Async.Loading -> {
                    TaskFlowUiState(isLoading = true)
                }
                is Async.Success -> {
                    TaskFlowUiState(itemFlow = asyncFlow.data, isLoading = loading)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskFlowUiState(isLoading = true)
        )
}
