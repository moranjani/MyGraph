package com.example.classictask.service

import kotlinx.coroutines.flow.Flow

interface IRandomService {
    suspend fun fetchRandomNumber() : Int
    suspend fun createRandomFlow() : Flow<Int>
}
