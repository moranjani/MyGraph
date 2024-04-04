package com.example.mygraph.service

import kotlinx.coroutines.flow.Flow

interface IRandomService {
    suspend fun fetchRandomNumber() : Int
    suspend fun createRandomFlow() : Flow<Int>
}
