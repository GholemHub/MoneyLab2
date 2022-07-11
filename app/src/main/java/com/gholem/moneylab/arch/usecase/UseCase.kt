package com.gholem.moneylab.arch.usecase

interface UseCase<IN : Any, OUT : Any> {

    suspend fun run(input: IN): OUT
}