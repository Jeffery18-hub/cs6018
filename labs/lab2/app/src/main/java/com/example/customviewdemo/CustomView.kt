package com.example.customviewdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    private val path = Path()

    // Ensure that the bitmap size matches the view size
    private fun ensureBitmap() {
        if (!::bitmap.isInitialized || bitmap.width != width || bitmap.height != height) {
            Log.e("height and width", "$height $width")
            bitmap = Bitmap.createBitmap(1080, 2208, Bitmap.Config.ARGB_8888)
            bitmapCanvas = Canvas(bitmap)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ensureBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }

    fun drawPoints(points: List<PointF>) {
        if (points.isEmpty()) return

        ensureBitmap()

        path.reset()
        val firstPoint = points[0]
        path.moveTo(firstPoint.x, firstPoint.y)

        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }

        bitmapCanvas.drawPath(path, paint)
        invalidate()  // This will trigger a redraw
    }
}
