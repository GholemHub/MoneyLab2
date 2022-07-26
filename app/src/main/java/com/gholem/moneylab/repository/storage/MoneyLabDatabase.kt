package com.gholem.moneylab.repository.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gholem.moneylab.repository.storage.MoneyLabDatabase.Companion.DATABASE_VERSION
import com.gholem.moneylab.repository.storage.dao.TemplateDao
import com.gholem.moneylab.repository.storage.dao.TransactionDao
import com.gholem.moneylab.repository.storage.entity.TemplateEntity
import com.gholem.moneylab.repository.storage.entity.TransactionEntity

@Database(
    version = DATABASE_VERSION,
    entities = [
        TemplateEntity::class,
        TransactionEntity::class
    ]
)
abstract class MoneyLabDatabase : RoomDatabase() {

    abstract fun templateDao(): TemplateDao
    abstract fun transactionDao(): TransactionDao

    companion object {

        const val DATABASE_VERSION = 1
    }
}