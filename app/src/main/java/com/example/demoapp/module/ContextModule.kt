package com.example.demoapp.module

/**
 * @author Chandan Kumar
 */
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ContextModule(context: Context) {
    var mContext: Context = context

    @Provides
    fun context(): Context {
        return mContext.getApplicationContext()
    }

}