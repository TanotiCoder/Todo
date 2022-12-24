package com.example.todu.data.local

import androidx.room.*
import com.example.todu.data.Task
import com.example.todu.utils.Common.Companion.tableName
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM $tableName")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM $tableName WHERE id = :id")
    fun getTaskFlow(id: Int): Flow<Task>

    @Query("SELECT * FROM $tableName")
    suspend fun getTasksList(): List<Task>

    @Query("SELECT * FROM $tableName WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Query("UPDATE $tableName SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun isCompleted(id: Int, isCompleted: Boolean)

    @Query("UPDATE $tableName SET isImportant = :isImportant WHERE id = :id")
    suspend fun isImportant(id: Int, isImportant: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM $tableName WHERE isCompleted = 1")
    fun completedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM $tableName WHERE isImportant = 1")
    fun importantTasks(): Flow<List<Task>>
}

