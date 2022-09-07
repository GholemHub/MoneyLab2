package com.gholem.moneylab.features.planning.domain

import com.gholem.moneylab.arch.usecase.UseCase
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.repository.network.TransactionApiRepository
import com.squareup.picasso.Downloader
import retrofit2.Response
import java.text.SimpleDateFormat
import javax.inject.Inject

class FetchTransactionModelUseCase @Inject constructor(
    private val transactionApiRepository: TransactionApiRepository,
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : UseCase<Unit, List<TransactionModel>> {

    override suspend fun run(input: Unit): List<TransactionModel> {
        val categories = getCategoryListUseCase.run(Unit)

        return transactionApiRepository.getTransaction()?.body()?.data?.map { person ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.parse(person.updated.toString())

            TransactionModel(
                category = categories.first { it.id == (person.rank.toInt() % categories.size).toLong() },
                amount = person.tradingPairs.toInt(),
                date = date.time
            )
        } ?: emptyList()
    }
}