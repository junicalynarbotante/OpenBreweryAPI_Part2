package com.example.openbreweryapi.realm.config

import io.realm.FieldAttribute
import io.realm.RealmMigration

val migration = RealmMigration{realm, oldVersion, newVersion ->
    if(oldVersion == 1L){
        val brewerySchema = realm.schema.get("BreweryRealm")
        brewerySchema?.let {
            it.removeField("breweryName")
            realm.schema.create("BreweryName")
                .addField("id", String::class.java,FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)

        }


    }

}
