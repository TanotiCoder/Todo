package com.example.todu.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

class Common {
    companion object {
        const val tableName = "tasks"
        const val titlePlaceholder = "Add Title....."
        const val taskPlaceholder = "Add your Task......"
        const val isCompleted = "isCompleted"
        const val message = "You have no Task,\n tap ‘+’ button to add Task"
        const val messageNoCompletedTask = "You have no Task Completed Task"
        const val messageNoBookmarkTask = "You have no Bookmark"

        val chipData: MutableList<RemoteChipData> = mutableListOf(
            RemoteChipData(0xff59A4FF, "Target"),
            RemoteChipData(0xffA0D468, "Action"),
            RemoteChipData(0xffFC657E, "Meting"),
            RemoteChipData(0xffFFC107, "Obstacle"),
            RemoteChipData(0xffFF5722, "Other")
        )

        val drawerButton: MutableList<DrawerContent> = mutableListOf(
            DrawerContent("Home", Icons.Filled.Home, "Home/{DrawerRoot}"),
            DrawerContent("Bookmark", Icons.Filled.Bookmark, "Bookmark/{DrawerRoot}"),
            DrawerContent("Done", Icons.Filled.DoneAll, "Done/{DrawerRoot}"),
        )
    }
}

data class RemoteChipData(var color: Long, var label: String)
data class DrawerContent(var route: String, var icon: ImageVector, var label: String)
