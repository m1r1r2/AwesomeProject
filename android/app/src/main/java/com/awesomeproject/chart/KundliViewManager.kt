package com.awesomeproject.chart

import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.bridge.ReadableMap

class KundliViewManager : SimpleViewManager<KundliView>() {

    override fun getName(): String {
        return "KundliView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): KundliView {
        return KundliView(reactContext)
    }

    // 1. Specify the type (view: KundliView) explicitly
    // 2. Call the correct method name (setData) defined in your KundliView class
    @ReactProp(name = "data")
    fun setData(view: KundliView, data: ReadableMap?) {
        data?.let {
            view.setData(it)
        }
    }
}
