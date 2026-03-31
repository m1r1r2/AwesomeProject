package com.awesomeproject.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.facebook.react.bridge.ReadableMap

class KundliView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var housesData: List<Map<String, Any>> = emptyList()

    // Paint objects for drawing
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }

    private val numPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        textAlign = Paint.Align.CENTER
    }
    private var lagnaSign: Int = 1

    // Called from React Native ViewManager
  fun setData(data: ReadableMap) {
    // ✅ ADD THIS LINE: It looks for 'ascendant' at the top level
    this.lagnaSign = if (data.hasKey("ascendant")) data.getInt("ascendant") else 1
    
    val houses = data.getArray("houses") ?: return
    val parsed = mutableListOf<Map<String, Any>>()
    
    for (i in 0 until houses.size()) {
        houses.getMap(i)?.let { dict ->
            val planet = dict.getString("planet") ?: ""
            val degree = dict.getString("degree") ?: ""
            val house = if (dict.hasKey("house")) {
                try { dict.getInt("house") } catch (e: Exception) { dict.getString("house")?.toIntOrNull() ?: 1 }
            } else { 1 }
            
            parsed.add(mapOf("planet" to planet, "degree" to degree, "house" to house))
        }
    }
    this.housesData = parsed
    invalidate() 
}



  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val w = width.toFloat()
    val h = height.toFloat()
    val s = minOf(w, h)
    val left = (w - s) / 2f
    val top = (h - s) / 2f
    val right = left + s
    val bottom = top + s
    val midX = left + s / 2f
    val midY = top + s / 2f

    canvas.drawColor(Color.WHITE)

    // 1. Draw Lines
    canvas.drawRect(left, top, right, bottom, linePaint)
    canvas.drawLine(left, top, right, bottom, linePaint)
    canvas.drawLine(right, top, left, bottom, linePaint)

    val diamondPath = Path().apply {
        moveTo(midX, top)
        lineTo(right, midY)
        lineTo(midX, bottom)
        lineTo(left, midY)
        close()
    }
    canvas.drawPath(diamondPath, linePaint)

    // 2. FINE-TUNED CENTERS (Pushed inward to avoid diagonal intersections)
    val centers = arrayOf(
        floatArrayOf(midX, top + s * 0.25f),              // House 1 (Diamond)
        floatArrayOf(left + s * 0.25f, top + s * 0.12f), // House 2 (Centered in Top-Left)
        floatArrayOf(left + s * 0.10f, top + s * 0.25f),   // House 3 (Triangle)
        floatArrayOf(left + s * 0.25f, midY),              // House 4 (Diamond)
        floatArrayOf(left + s * 0.08f, top + s * 0.80f),   // House 5 (Triangle)
        floatArrayOf(left + s * 0.25f, top + s * 0.90f),   // House 6 (Triangle)
        floatArrayOf(midX, top + s * 0.75f),              // House 7 (Diamond)
        floatArrayOf(right - s * 0.25f, top + s * 0.88f),  // House 8 (Bottom-Right) - Moved Inward
        floatArrayOf(right - s * 0.12f, top + s * 0.75f),
        floatArrayOf(right - s * 0.25f, midY),             // House 10 (Diamond)
        floatArrayOf(right - s * 0.12f, top + s * 0.25f),  // House 11 (Top-Right) - Moved Inward
       floatArrayOf(right - s * 0.25f, top + s * 0.12f)    // House 12 (Triangle)
    )

    val groups = housesData.groupBy { it["house"] as Int }

    for (i in 1..12) {
        val cx = centers[i - 1][0]
        val cy = centers[i - 1][1]

        var signToDraw = (lagnaSign + i - 1)
        while (signToDraw > 12) signToDraw -= 12
        
        // 3. DYNAMIC SIGN POSITIONING
        // Move signs further up in triangles to save space for planets
        val isDiamond = (i == 1 || i == 4 || i == 7 || i == 10)
        val signOffset = if (isDiamond) s * 0.10f else s * 0.07f
        
        numPaint.textSize = s * 0.042f 
        canvas.drawText(signToDraw.toString(), cx, cy - signOffset, numPaint)

        // 4. PLANET DRAWING
        groups[i]?.let { planets ->
            textPaint.apply {
                textSize = s * 0.034f // Balanced size for readability
                isFakeBoldText = true
            }
            
            val metrics = textPaint.fontMetrics
            val lineHeight = (metrics.descent - metrics.ascent) * 0.90f
            
            // Re-centering the planet block slightly lower to avoid sign collision
            var startY = cy - (planets.size * lineHeight / 2f) - metrics.ascent + (s * 0.015f)
            
            for (p in planets) {
                val label = "${p["planet"]} ${p["degree"]}"
                canvas.drawText(label, cx, startY, textPaint)
                startY += lineHeight
            }
        }
    }
}





}
