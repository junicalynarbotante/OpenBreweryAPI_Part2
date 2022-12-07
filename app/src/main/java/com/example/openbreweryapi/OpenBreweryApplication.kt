package com.example.openbreweryapi

import android.app.Application
import io.realm.Realm

class OpenBreweryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}