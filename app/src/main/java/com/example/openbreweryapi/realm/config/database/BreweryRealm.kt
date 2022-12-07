package com.example.openbreweryapi.realm.config.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class BreweryRealm (
    @PrimaryKey
    var id: String = "",
    @Required
    var name: String = "",
    var type: String = "",
    @Required
    var phone: String = "",
    var state: String = "",
    var country: String = ""
):RealmObject()