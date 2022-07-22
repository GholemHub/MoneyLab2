package com.gholem.moneylab.repository.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

        @Volatile
        private var INSTANCE: MoneyLabDatabase? = null

        fun getDatabase(context: Context): MoneyLabDatabase{
            val temoInstant = INSTANCE
            if(temoInstant != null){
                return temoInstant
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoneyLabDatabase::class.java,
                    "transaction_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }


        const val DATABASE_VERSION = 1
    }
}