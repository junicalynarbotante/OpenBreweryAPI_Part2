package com.example.openbreweryapi.realm.config.operation

import com.example.openbreweryapi.models.listBreweryAPI.BreweryModelItem
import com.example.openbreweryapi.realm.config.database.BreweryRealm
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class OperationBrewery (private var config: RealmConfiguration) {
    suspend fun insertBrewery(id: String,
                              name: String,
                              type:String,
                              phone: String,
                              state: String,
                              country: String
    ) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val brewery = BreweryRealm(id = id, name = name,type = type,phone = phone, state= state, country = country)
            realmTransaction.insert(brewery)
        }
    }

    suspend fun filterBrewery(query: String): ArrayList<BreweryModelItem>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<BreweryModelItem>()
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(BreweryRealm::class.java)
                .contains("id",query, Case.INSENSITIVE)
                .findAll()
                .map {
                    mapBrewery(it)
                })
        }

        return realmResults
    }

    suspend fun retrieveBrewery():ArrayList<BreweryModelItem>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<BreweryModelItem>()

        realm.executeTransactionAwait (Dispatchers.IO) { realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(BreweryRealm::class.java)
                .findAll()
                .map {
                    mapBrewery(it)
                })
        }
        return realmResults
    }

    suspend fun removeBrewery(breweryID : String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val breweryToRemove = realmTransaction
                .where(BreweryRealm::class.java)
                .equalTo("id",breweryID)
                .findFirst()
            breweryToRemove?.deleteFromRealm()
        }
    }

    private fun mapBrewery(brewery: BreweryRealm): BreweryModelItem {
        return BreweryModelItem(
            id = brewery.id,
            name = brewery.name,
            type = brewery.type,
            phone = brewery.phone,
            state = brewery.state,
            country = brewery.country
        )
    }


}