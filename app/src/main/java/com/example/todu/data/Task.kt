package com.example.todu.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todu.utils.Common.Companion.tableName


@Entity(tableName = tableName)
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var index: Int = 0,
    var date: String,
    var isCompleted: Boolean = false,
    var isImportant:Boolean = false
)


