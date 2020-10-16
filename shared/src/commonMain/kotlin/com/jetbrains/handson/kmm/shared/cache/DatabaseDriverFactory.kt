package com.jetbrains.handson.kmm.shared.cache

import com.squareup.sqldelight.db.SqlDriver
import com.jetbrains.handson.kmm.shared.cache.AppDatabase

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}