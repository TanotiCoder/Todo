package com.example.todu.di

import android.content.Context
import androidx.room.Room
import com.example.todu.data.local.TaskDao
import com.example.todu.data.local.TaskDb
import com.example.todu.repository.TaskRepository
import com.example.todu.repository.TaskRepositoryImpl
import com.example.todu.utils.Common.Companion.tableName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    fun provideTaskDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TaskDb::class.java, tableName)
            .build()

    @Provides
    fun provideTaskDao(taskDb: TaskDb) = taskDb.taskDao()

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)
}

