package com.example.todu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todu.data.Task


@Database(entities = [Task::class], version = 11, exportSchema = false)
abstract class TaskDb() : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}


