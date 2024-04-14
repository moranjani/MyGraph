package com.example.classictask.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classictask.service.RandomService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: RandomService,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


//    private lateinit var dataFlow: Flow<Int>
//    private val _data: MutableLiveData<List<Float>> = MutableLiveData<List<Float>>().apply {
//        value = listOf()
//
//    }
//    val data: LiveData<List<Float>> = _data


    private val dataByClickList = mutableListOf<Int>()
    private val _dataByClick: MutableLiveData<List<Int>> = MutableLiveData<List<Int>>()
        .apply {
            value = emptyList()
        }
    val dataByClick: LiveData<List<Int>> = _dataByClick


    private var job: Job? = null
    private val mutex = Mutex()



    fun onAddNumberClicked() {
        job = viewModelScope.launch(ioDispatcher) {
            addDataToList()
        }
    }
    private suspend fun addDataToList() {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                try {
                    val randomNumber = service.fetchRandomNumber()
                    dataByClickList.add(randomNumber)
                    _dataByClick.postValue(dataByClickList)
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching random number: ${e.message}", e)
                }
            }
        }
    }

    fun stopRunning() {
        job?.cancel()
    }

//    private suspend fun produceDataFlow() {
//        withContext(Dispatchers.IO) {
//            dataFlow = service.createRandomFlow()
//        }
//    }
//
//    private suspend fun consumeData() {
//        withContext(Dispatchers.Default) {
//            val lastTen = mutableListOf<Float>()
//            dataFlow
//                .map { it.toFloat() }
//                .buffer(10)
//                .collect { number ->
//                    if (lastTen.size >= 10) {
//                        lastTen.removeFirst()
//                    }
//                    lastTen.add(number)
//                    _data.postValue(lastTen)
//                }
//        }
//    }
//
//    fun startRunning() {
//        job = viewModelScope.launch(ioDispatcher) {
//            produceDataFlow()
//            consumeData()
//        }
//    }
//




}