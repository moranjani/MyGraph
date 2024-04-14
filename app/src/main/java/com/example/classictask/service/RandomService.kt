package com.example.classictask.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomService @Inject constructor(private val api: RandomApi): IRandomService {
    override suspend fun fetchRandomNumber(): Int {
        return api.fetchRandomNumber()
    }

    override suspend fun createRandomFlow() : Flow<Int> {
        val randomFlow: Flow<Int> = flow {
            while(true) {
                val randomInt = fetchRandomNumber()
                emit(randomInt)
                delay(10)
            }
        }
        return randomFlow
    }
}