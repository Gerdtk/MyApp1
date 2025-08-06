package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject

class VCRUD (private val realm: Realm) {

        fun insertar(viaje: Viaje) {
            realm.writeBlocking {
                copyToRealm(viaje)
            }
            Log.d("REALM", "Viaje insertado")
        }

        fun obtenerTodos(): List<Viaje> {
            return realm.query<Viaje>().find()
        }

        fun eliminar(id: String) {
            realm.writeBlocking {
                val viaje = query<Viaje>("idv == $0", id).first().find()
                viaje?.let {
                    delete(it)
                } ?: Log.e("REALM", "Viaje con ID $id no encontrado")
            }
        }

        fun modificar(id: String, nuevotipo: String, nuevafechaOut: String, nuevafechaIn: String, nuevoDestino: String) {
            realm.writeBlocking {
                val viaje = query<Viaje>("idv == $0", id).first().find()
                viaje?.apply {
                    this.typo = nuevotipo
                    this.fechaIn = nuevafechaIn
                    this.fechaOut = nuevafechaOut
                    this.destino = nuevoDestino
                } ?: Log.e("Gato", "No se encontró viaje para modificar con ID: $id")
            }
        }

    }

