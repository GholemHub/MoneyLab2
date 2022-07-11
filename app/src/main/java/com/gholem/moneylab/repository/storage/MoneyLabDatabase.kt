package com.gholem.moneylab.repository.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gholem.moneylab.repository.storage.MoneyLabDatabase.Companion.DATABASE_VERSION
import com.gholem.moneylab.repository.storage.dao.TemplateDao
import com.gholem.moneylab.repository.storage.entity.TemplateEntity

@Database(
    version = DATABASE_VERSION,
    entities = [
        TemplateEntity::class
    ]
)
abstract class MoneyLabDatabase : RoomDatabase() {

    abstract fun templateDao(): TemplateDao

    companion object {

        const val DATABASE_VERSION = 1
    }
}