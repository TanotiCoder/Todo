package com.example.todu.repository

import com.example.todu.data.Task
import com.example.todu.data.local.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override fun getTaskFlow(id: Int): Flow<Task> {
        return taskDao.getTaskFlow(id)
    }

    override suspend fun getTaskList(): List<Task> {
        return taskDao.getTasksList()
    }

    override suspend fun getTask(id: Int): Task {
        return taskDao.getTask(id)
    }

    override suspend fun checkIsCompleted(id: Int, isCompleted: Boolean) {
        return taskDao.isCompleted(id, isCompleted)
    }

    override suspend fun checkIsImportant(id: Int, isImportant: Boolean) {
        return taskDao.isImportant(id, isImportant)
    }

    override suspend fun insertTask(task: Task) {
        return taskDao.addTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }

    override suspend fun refreshTasks() {

    }

    override fun getCompletedTask(): Flow<List<Task>> {
        return taskDao.completedTasks()
    }

    override fun getImportantTask(): Flow<List<Task>> {
        return taskDao.importantTasks()
    }
}