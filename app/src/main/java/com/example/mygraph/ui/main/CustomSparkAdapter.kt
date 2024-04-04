package com.example.mygraph.ui.main

import com.robinhood.spark.SparkAdapter


class CustomSparkAdapter(private val yData: FloatArray) : SparkAdapter() {

    override fun getCount(): Int {
        return yData.size
    }

    override fun getItem(index: Int): Float {
        return yData[index]
    }

    override fun getY(index: Int): Float {
        return yData[index]
    }
}

