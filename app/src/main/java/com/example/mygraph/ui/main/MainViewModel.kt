package com.example.mygraph.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygraph.service.RandomService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: RandomService,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _data: MutableLiveData<List<Float>> = MutableLiveData<List<Float>>().apply {
        value = listOf()

    }
    val data: LiveData<List<Float>> = _data

    private lateinit var dataFlow: Flow<Int>
    private var job: Job? = null

    private suspend fun produceData() {
        withContext(Dispatchers.IO) {
            dataFlow = service.createRandomFlow()
        }
    }

    private suspend fun consumeData() {
        withContext(Dispatchers.Default) {
            val lastTen = mutableListOf<Float>()
            dataFlow
                .map { it.toFloat() }
                .buffer(10)
                .collect { number ->
                    if (lastTen.size >= 10) {
                        lastTen.removeFirst()
                    }
                    lastTen.add(number)
                    _data.postValue(lastTen)
                }
        }
    }

    fun startRunning() {
        job = viewModelScope.launch(ioDispatcher) {
            produceData()
            consumeData()
        }
    }

    fun stopRunning() {
        job?.cancel()
    }



}