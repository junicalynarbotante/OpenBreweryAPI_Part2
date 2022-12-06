package com.example.openbreweryapi.realm.config

import io.realm.RealmConfiguration

private const val realmVersion = 2L

object RealmConfig {
    fun getConfiguration(): RealmConfiguration{
        return RealmConfiguration.Builder()
            .schemaVersion(realmVersion)
            .migration(migration)
            .build()
    }
}