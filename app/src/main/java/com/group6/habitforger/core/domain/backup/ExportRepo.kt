package com.group6.habitforger.core.domain.backup

interface ExportRepo {
    suspend fun exportToJson()
}

enum class ExportState {
    IDLE,
    EXPORTING,
    EXPORTED
}