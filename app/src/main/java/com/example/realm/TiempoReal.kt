package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.ext.query
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class TiempoReal(private val realm: Realm) {

    private var job: Job? = null

    fun observarCambios() {
        job = CoroutineScope(Dispatchers.Default).launch {
            realm.query<Usuario>().asFlow()
                .collect { resultsChange->
                    val listaActual = resultsChange.list
                    Log.d("REALM", "Lista de productos actual: ${listaActual.size}")
                    }
                }
        }

    fun detener() {
        job?.cancel()
    }
}

