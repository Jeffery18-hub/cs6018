package com.example.customviewdemo

import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SimpleViewModel :ViewModel() {

    private val _points = MutableLiveData<MutableList<PointF>>().apply {
        value = mutableListOf()
    }

    val points: LiveData<MutableList<PointF>> = _points

    fun addPoint(point: PointF) {
        _points.value?.add(point)
        _points.postValue(_points.value)
    }
//
//    fun clearPoints() {
//        _points.value?.clear()
//        _points.postValue(_points.value)
//    }
}