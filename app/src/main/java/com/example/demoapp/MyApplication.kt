package com.example.demoapp

/**
 * @author Chandan Kumar
 */
import android.app.Activity
import android.app.Application
import com.example.demoapp.component.DaggerDemoAppComponent
import com.example.demoapp.component.DemoAppComponent
import com.example.demoapp.module.ContextModule


class MyApplication : Application() {
    private var demoAppComponent: DemoAppComponent? = null

    override fun onCreate() {
        super.onCreate()
        demoAppComponent = DaggerDemoAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    fun getDemoAppComponent(): DemoAppComponent? {
        return demoAppComponent
    }

    companion object {
        operator fun get(activity: Activity): MyApplication {
            return activity.application as MyApplication
        }
    }
}