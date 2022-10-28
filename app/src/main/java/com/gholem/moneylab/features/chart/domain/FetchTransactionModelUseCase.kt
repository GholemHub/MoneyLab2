package com.gholem.moneylab.features.chart.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.repository.network.TransactionApiRepository

import javax.inject.Inject

class FetchTransactionModelUseCase @Inject constructor(
    private val transactionApiRepository: TransactionApiRepository,
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : UseCase<Unit, List<TransactionModel>> {

    override suspend fun run(input: Unit): List<TransactionModel> {
        val categories = getCategoryListUseCase.run(Unit)

        return transactionApiRepository.getTransaction()?.body()?.data?.map { person ->
            var categoryItem = categories.first {
                when (it) {
                    is CategoryItem.ExpenseCategoryModel -> {
                        it.id == (person.rank.toInt() % categories.size).toLong() + 1
                    }
                    is CategoryItem.IncomeCategoryModel -> {
                        it.id == (person.rank.toInt() % categories.size).toLong() + 1
                    }
                }
            }
            TransactionModel(
                category = categoryItem,
                amount = person.tradingPairs.toInt(),
                date = person.updated,
                transactionId = System.currentTimeMillis()
            )
        } ?: emptyList()
    }
}