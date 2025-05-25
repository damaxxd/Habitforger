package com.group6.habitforger.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.group6.habitforger.core.domain.AlarmScheduler
import com.group6.habitforger.core.data.DatastoreFactory
import com.group6.habitforger.core.data.DataStoreImpl
import com.group6.habitforger.core.data.backup.export.ExportImpl
import com.group6.habitforger.core.data.backup.restore.RestoreImpl
import com.group6.habitforger.core.domain.backup.RestoreRepo
import com.group6.habitforger.core.domain.backup.ExportRepo
import com.group6.habitforger.core.domain.HBDatastore
import com.group6.habitforger.habits.data.database.HabitDatabase
import com.group6.habitforger.habits.data.database.HabitDbFactory
import com.group6.habitforger.tasks.data.database.TaskDatabase
import com.group6.habitforger.tasks.data.database.TaskDbFactory
import com.group6.habitforger.habits.data.repository.HabitRepository
import com.group6.habitforger.core.data.NotificationAlarmScheduler
import com.group6.habitforger.habits.domain.HabitRepo
import com.group6.habitforger.tasks.data.repository.TasksRepository
import com.group6.habitforger.viewmodels.HabitViewModel
import com.group6.habitforger.tasks.domain.TaskRepo
import com.group6.habitforger.viewmodels.SettingsViewModel
import com.group6.habitforger.viewmodels.StateLayer
import com.group6.habitforger.viewmodels.TasksViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // databases
    singleOf(::HabitDbFactory)
    singleOf(::TaskDbFactory)
    single {
        get<HabitDbFactory>()
            .create()
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<TaskDbFactory>()
            .create()
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<HabitDatabase>().habitDao() }
    single { get<HabitDatabase>().habitStatusDao() }
    single { get<TaskDatabase>().taskDao() }
    single { get<TaskDatabase>().categoryDao() }

    singleOf(::HabitRepository).bind<HabitRepo>()
    singleOf(::TasksRepository).bind<TaskRepo>()

    singleOf(::ExportImpl).bind<ExportRepo>()
    singleOf(::RestoreImpl).bind<RestoreRepo>()

    // Datastore
    singleOf(::DatastoreFactory)
    single { get<DatastoreFactory>().getPreferencesDataStore() }
    singleOf(::DataStoreImpl).bind<HBDatastore>()

    // scheduler
    singleOf(::NotificationAlarmScheduler).bind<AlarmScheduler>()

    // view models
    singleOf(::StateLayer)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HabitViewModel)
    viewModelOf(::TasksViewModel)
}