package com.awesomeproject.chart

import android.view.ViewGroup
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.bridge.ReadableArray

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MyLineChartManager : SimpleViewManager<LineChart>() {

    override fun getName() = "MyLineChart"

    override fun createViewInstance(reactContext: ThemedReactContext): LineChart {
        val chart = LineChart(reactContext)

        chart.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        return chart
    }

    @ReactProp(name = "data")
    fun setData(chart: LineChart, data: ReadableArray) {

        val entries = ArrayList<Entry>()

        for (i in 0 until data.size()) {
            val value = data.getDouble(i).toFloat()
            entries.add(Entry(i.toFloat(), value))
        }

        val dataSet = LineDataSet(entries, "My Data")
        val lineData = LineData(dataSet)

        chart.data = lineData
        chart.invalidate()
    }
}