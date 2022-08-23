package com.gholem.moneylab.repository.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gholem.moneylab.repository.storage.MoneyLabDatabase.Companion.DATABASE_VERSION
import com.gholem.moneylab.repository.storage.converters.DatabaseTypeConverters
import com.gholem.moneylab.repository.storage.dao.CategoryDao
import com.gholem.moneylab.repository.storage.dao.TemplateDao
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.CategoryEntity
import com.gholem.moneylab.repository.storage.entity.TemplateEntity
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@TypeConverters(DatabaseTypeConverters::class)
@Database(
    version = DATABASE_VERSION,
    entities = [
        TemplateEntity::class,
        TransactionEntity::class,
        CategoryEntity::class
    ]
)
abstract class MoneyLabDatabase : RoomDatabase() {

    abstract fun templateDao(): TemplateDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        const val DATABASE_VERSION = 1
    }
}