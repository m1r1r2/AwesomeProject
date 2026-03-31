package com.awesomeproject

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

import com.awesomeproject.chart.KundliViewManager

class MyAppPackage : ReactPackage {
    override fun createViewManagers(
    reactContext: ReactApplicationContext
): MutableList<ViewManager<View, ReactShadowNode<*>>> {
    val managers = mutableListOf<ViewManager<View, ReactShadowNode<*>>>()
    managers.add(KundliViewManager() as ViewManager<View, ReactShadowNode<*>>)
    return managers
}
    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): MutableList<NativeModule> =
        mutableListOf(CalendarModule(reactContext))
}