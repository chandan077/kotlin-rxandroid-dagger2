package com.example.demoapp.component

import com.example.demoapp.module.NetworkServiceModule
import com.example.demoapp.interfaces.RemoteApiService
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkServiceModule::class])
interface DemoAppComponent {
    val remoteApiService: RemoteApiService
}