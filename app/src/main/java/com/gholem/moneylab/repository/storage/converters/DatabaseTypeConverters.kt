package com.gholem.moneylab.repository.storage.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import com.gholem.moneylab.util.fromJson
import com.google.gson.Gson

@ProvidedTypeConverter
class DatabaseTypeConverters {

    @TypeConverter
    fun transactionEntityListToJson(transactions: List<TransactionEntity>): String =
        Gson().toJson(transactions)

    @TypeConverter
    fun transactionEntityListFromJson(json: String): List<TransactionEntity> =
        Gson().fromJson(json)
}