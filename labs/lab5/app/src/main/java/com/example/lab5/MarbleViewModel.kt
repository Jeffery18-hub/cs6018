package com.example.lab5

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MarbleViewModel : ViewModel() {
    val ballPosition = MutableLiveData(Offset(0f, 0f))

    fun updatePosition(x: Float, y: Float) {
        ballPosition.value = Offset(x, y)
    }
}
