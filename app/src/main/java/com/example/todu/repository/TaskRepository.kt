package com.example.todu.repository

import com.example.todu.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository{
    fun getTasks():Flow<List<Task>>
    fun getTaskFlow(id: Int):Flow<Task>
    suspend fun getTaskList():List<Task>
    suspend fun getTask(id:Int):Task
    suspend fun checkIsCompleted(id: Int, isCompleted: Boolean)
    suspend fun checkIsImportant(id: Int, isImportant: Boolean)
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun refreshTasks()
    fun getCompletedTask():Flow<List<Task>>
    fun getImportantTask():Flow<List<Task>>
}

