package com.gr6.habitforger.core.data.backup.export

import android.os.Environment
import com.gr6.habitforger.core.data.backup.ExportSchema
import com.gr6.habitforger.core.data.backup.toCategorySchema
import com.gr6.habitforger.core.data.backup.toHabitSchema
import com.gr6.habitforger.core.data.backup.toHabitStatusSchema
import com.gr6.habitforger.core.data.backup.toTaskSchema
import com.gr6.habitforger.core.domain.backup.ExportRepo
import com.gr6.habitforger.habits.domain.HabitRepo
import com.gr6.habitforger.tasks.domain.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime

class ExportImpl(
    private val taskRepo: TaskRepo,
    private val habitsRepo: HabitRepo
): ExportRepo {
    override suspend fun exportToJson() = coroutineScope {
        val habitsDef = async {
            withContext(Dispatchers.IO) {
                habitsRepo.getHabits().map { it.toHabitSchema() }
            }
        }.await()

        val statusesDef = async {
            withContext(Dispatchers.IO) {
                habitsRepo.getHabitStatuses().map { it.toHabitStatusSchema() }
            }
        }.await()

        val tasksDef = async {
            withContext(Dispatchers.IO) {
                taskRepo.getTasks().map { it.toTaskSchema() }
            }
        }.await()

        val categoriesDef = async {
            withContext(Dispatchers.IO) {
                taskRepo.getCategories().map { it.toCategorySchema() }
            }
        }.await()

        val exportFolder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Habitforger"
        )

        if (!exportFolder.exists() || !exportFolder.isDirectory) exportFolder.mkdirs()

        val time = LocalDateTime.now().toString().replace(":", "").replace(" ", "")
        val file = File(exportFolder, "Habitforger-Export-$time.json")

        file.writeText(
            Json.encodeToString(
                ExportSchema(
                    schemaVersion = 1,
                    habits = habitsDef,
                    habitStatus = statusesDef,
                    tasks = tasksDef,
                    categories = categoriesDef
                )
            )
        )
    }
}