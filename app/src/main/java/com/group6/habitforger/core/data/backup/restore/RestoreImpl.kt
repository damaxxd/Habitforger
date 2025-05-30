package com.group6.habitforger.core.data.backup.restore

import android.content.Context
import android.net.Uri
import com.group6.habitforger.core.data.backup.ExportSchema
import com.group6.habitforger.core.data.backup.toCategory
import com.group6.habitforger.core.data.backup.toHabit
import com.group6.habitforger.core.data.backup.toHabitStatus
import com.group6.habitforger.core.data.backup.toTask
import com.group6.habitforger.core.domain.AlarmScheduler
import com.group6.habitforger.core.domain.backup.RestoreFailedException
import com.group6.habitforger.core.domain.backup.RestoreRepo
import com.group6.habitforger.core.domain.backup.RestoreResult
import com.group6.habitforger.habits.domain.HabitRepo
import com.group6.habitforger.tasks.domain.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.io.path.outputStream
import kotlin.io.path.readText

class RestoreImpl(
    private val taskRepo: TaskRepo,
    private val habitRepo: HabitRepo,
    private val alarmScheduler: AlarmScheduler,
    private val context: Context
): RestoreRepo {
    override suspend fun restoreData(uri: Uri): RestoreResult {
        return try {
            val file = kotlin.io.path.createTempFile()

            context.contentResolver.openInputStream(uri).use { input ->
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }
            }

            val json = Json {
                ignoreUnknownKeys = true
            }

            val jsonDeserialized = json.decodeFromString<ExportSchema>(file.readText())

            withContext(Dispatchers.IO) {
                awaitAll(
                    async {
                        habitRepo.getHabits().forEach { alarmScheduler.cancel(it) }
                        alarmScheduler.cancelAll()

                        jsonDeserialized.habits.map { it.toHabit() }.forEach {
                            habitRepo.upsertHabit(it)
                            alarmScheduler.schedule(it)
                        }

                        jsonDeserialized.habitStatus.map { it.toHabitStatus() }.forEach {
                            habitRepo.insertHabitStatus(it)
                        }
                    },
                    async {
                        jsonDeserialized.categories.map { it.toCategory() }.forEach {
                            taskRepo.upsertCategory(it)
                        }

                        jsonDeserialized.tasks.map { it.toTask() }.forEach {
                            taskRepo.upsertTask(it)
                        }
                    }
                )
            }

            RestoreResult.Success
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            RestoreResult.Failure(RestoreFailedException.InvalidFile)
        } catch (e: SerializationException) {
            e.printStackTrace()
            RestoreResult.Failure(RestoreFailedException.OldSchema)
        }
    }
}