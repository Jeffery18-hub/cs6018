package com.example.lab5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData


class MarbleActivity : ComponentActivity(), SensorEventListener {
    private val viewModel: MarbleViewModel by viewModels()
    private val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager }
    private var gravitySensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        setContent {
            MarbleScreen(viewModel.ballPosition)
        }
    }

    override fun onResume() {
        super.onResume()
        gravitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val scaleFactor = 100f
            val x = -it.values[0]*scaleFactor
            val y = -it.values[1]*scaleFactor// To account for the inverted y-axis
            viewModel.updatePosition(x, y)
        }
    }
}



@Composable
fun MarbleScreen(position: LiveData<Offset>) {
    val offset by position.observeAsState(initial = Offset(0f, 0f))
    Log.e("offset detect", offset.toString())
    CustomBox(offset)
}

@Composable
fun CustomBox(offset: Offset) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BoxWithConstraints(
            modifier = Modifier
                .size(300.dp)
                .border(2.dp, Color.Black)
        ) {
            val maxWidth = this.maxWidth
            val maxHeight = this.maxHeight
           Log.e("box-constraint",
               "maxWidth: ${maxWidth} , maxHeight: ${maxHeight}")
            val circleRadius = 50f

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawCircle(
                    color = Color.Red,
                    radius = circleRadius,
                    center = Offset(
                        (maxWidth.toPx()/2 - offset.x).coerceIn(circleRadius, maxWidth.toPx() - circleRadius),
                        (maxHeight.toPx() - offset.y).coerceIn(circleRadius, maxHeight.toPx() - circleRadius))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBoxPreview(){
    CustomBox(Offset(100F, 100F))
}


//fun getGravityData(sensor: Sensor, sensorManager: SensorManager) : Flow<Offset> {
//    return channelFlow {
//        val listener = object : SensorEventListener {
//            override fun onSensorChanged(event: SensorEvent?) {
//                event?.let {
//                    val x = it.values[0]
//                    val y = -it.values[1]
//                    channel.trySend(Offset(x, y))
//                }
//            }
//
//            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//        }
//
//        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME)
//
//        awaitClose {
//            sensorManager.unregisterListener(listener)
//        }
//    }
//}



