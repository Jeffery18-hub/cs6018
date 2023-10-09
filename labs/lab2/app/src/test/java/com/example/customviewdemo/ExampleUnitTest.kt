package com.example.customviewdemo

import android.graphics.Color
import android.graphics.PointF
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SimpleViewModelTest {

    private lateinit var viewModel: SimpleViewModel

    @Before
    fun setup() {
        viewModel = SimpleViewModel()
    }

    @Test
    fun testAddPoint() {
        val point = MyPoint(point = PointF(5.0f, 5.0f), color = Color.RED, size = 10f, shape = "Circle")
        viewModel.addPoint(point.point, point.color, point.size, point.shape)
        Assert.assertEquals(point, viewModel.points.value?.last())
    }

    @Test
    fun testSelectColor() {
        viewModel.selectColor("Blue")
        Assert.assertEquals("Blue", viewModel.selectedColor.value)
    }

    @Test
    fun testSelectSize() {
        viewModel.selectSize("10")
        Assert.assertEquals("10", viewModel.selectedSize.value)
    }

    @Test
    fun testSelectShape() {
        viewModel.selectShape("Rectangle")
        Assert.assertEquals("Rectangle", viewModel.selectedShape.value)
    }
}